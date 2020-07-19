package org.example.be.service;

import io.debezium.outbox.quarkus.ExportedEvent;
import io.quarkus.panache.common.Sort;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.eclipse.microprofile.opentracing.Traced;
import org.example.be.model.entity.*;
import org.example.be.model.event.reply.ClassificationReplyEvent;
import org.example.be.model.event.reply.ClassificationStatusEnum;
import org.example.be.model.event.request.ClassificationRequestEvent;
import org.example.be.model.json.ClassificationGetAllResponse;
import org.example.be.model.json.ClassificationGetByIdResponse;
import org.example.be.model.json.ClassificationRequest;
import org.example.be.repository.ClassificationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Traced
@ApplicationScoped
public class ClassificationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationService.class);

    private final HashMap<UUID, Instant> consumedEventsMap = new HashMap<>();

    @Inject
    EventBus bus;

    @Inject
    S3Service s3Service;

    @Inject
    ClassificationRepository classificationRepository;

    @Inject
    Event<ExportedEvent<?, ?>> event;

    /**
     * Read all classifications
     * <p>
     * It should order by date;
     * It shouldn't return CANCELED classifications (soft delete);
     * It should only return a maximum of 20 elements.
     *
     * @return List w/ classification responses
     */
    public List<ClassificationGetAllResponse> getAll() {
        LOGGER.info("getAll() method called");

        return classificationRepository
                .streamAll(Sort.descending(Classification.CREATED_AT_FIELD))
                .filter(classification -> classification.getClassificationResult().getStatus() != StatusEnum.DELETED)
                .map(ClassificationGetAllResponse::new)
                .limit(20)
                .collect(Collectors.toList());

        /*return getStaticListWithClassifications()
                .stream()
                .map(ClassificationResponse::new)
                .collect(Collectors.toList());*/
    }

    /**
     * Read one classification
     *
     * @param id Classification ID
     * @return Classification response
     */
    public ClassificationGetByIdResponse getById(UUID id) {
        LOGGER.info("getById() method called");

        final Classification classification = classificationRepository.findById(id);

        if (classification == null) {
            throw new NoSuchElementException("No image classification with id " + id);
        }

        return new ClassificationGetByIdResponse(classification);
    }

    /**
     * Create new classification
     * <p>
     * It should upload image file to S3 bucket;
     * It should save image metadata on database;
     * It should save outbox event.
     *
     * @param request Classification request
     * @return UUID Classification ID
     */
    @Transactional
    public UUID insert(ClassificationRequest request) {
        LOGGER.info("insert() method called");

        final UUID id = UUID.randomUUID();

        // Create temporary file
        final File temporaryFile = createTemporaryFile(request.getFile());

        // Upload to S3 bucket
        final String url = s3Service.upload(id, temporaryFile, request.getMimeType());

        // Save metadata on database
        final ImageMetadata imageMetadata = new ImageMetadata(request.getFileName(), temporaryFile.length(), request.getMimeType(), url);
        final ClassificationResult classificationResult = new ClassificationResult(StatusEnum.PENDING);
        final Classification classification = new Classification(id, request.getClassificationName(), request.getClassificationDescription(), LocalDateTime.now(), imageMetadata, classificationResult);
        classificationRepository.persist(classification);

        // Publish outbox event
        event.fire(ClassificationRequestEvent.of(id, url));

        return id;
    }

    /**
     * Consumes data from "update-classification" address on event bus.
     * Event is then sent again to event bus through "produce-sse-notification" address.
     *
     * @param message Message w/ classification reply event
     */
    @ConsumeEvent(value = "update-classification", blocking = true)
    @Transactional
    public void update(Message<ClassificationReplyEvent> message) {
        LOGGER.info("update() method called");

        try {
            final ClassificationReplyEvent event = message.body();

            // Check if event was already consumed (in-memory validation)
            isConsumed(event.getId());

            final Classification classification = classificationRepository.findById(event.getId());

            if (classification == null) {
                throw new NoSuchElementException("No image classification with id " + event.getId());
            }

            // Insert classification tags
            final List<ClassificationTag> tags = event.getTags()
                    .stream()
                    .map(tag -> new ClassificationTag(tag.getName(), tag.getConfidence(), classification.getClassificationResult()))
                    .collect(Collectors.toList());
            classification.getClassificationResult().setTags(tags);

            // Update classification result status
            if (event.getStatus() == ClassificationStatusEnum.ERROR) {
                classification.getClassificationResult().setStatus(StatusEnum.ERROR);
            } else {
                classification.getClassificationResult().setStatus(StatusEnum.ACTIVE);
            }

            // Update classification update date
            classification.setUpdatedAt(LocalDateTime.now());

            classificationRepository.persist(classification);

            bus.sendAndForget("produce-sse-notification", new ClassificationGetAllResponse(classification));
        } catch (NoSuchElementException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Delete classification
     *
     * @param id Classification ID
     */
    @Transactional
    public void delete(UUID id) {
        LOGGER.info("delete() method called");

        final Classification classification = classificationRepository.findById(id);

        if (classification == null) {
            throw new NoSuchElementException("No image classification with id " + id);
        }

        classification.getClassificationResult().setStatus(StatusEnum.DELETED);

        classificationRepository.persist(classification);
    }

    /**
     * Create temporary file based on image data
     *
     * @param data Image data
     * @return File
     */
    private File createTemporaryFile(InputStream data) {
        final File temporaryPath;

        try {
            temporaryPath = File.createTempFile("upload", ".tmp");
            Files.copy(data, temporaryPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return temporaryPath;
    }

    /**
     * Checks if event was already consumed in order
     * to avoid duplicate event consumption
     *
     * @param id Classification ID
     */
    private void isConsumed(UUID id) {
        if (consumedEventsMap.containsKey(id)) {
            throw new RuntimeException("Event with id " + id + " was already consumed at " + consumedEventsMap.get(id));
        } else {
            consumedEventsMap.put(id, Instant.now());
        }
    }

    /**
     * Get static list w/ classifications (useful for testing UI service)
     *
     * @return List w/ classifications
     */
    private List<Classification> getStaticListWithClassifications() {
        return List.of(
                new Classification(
                        UUID.randomUUID(),
                        "Image #1",
                        "",
                        LocalDateTime.of(2013, 10, 4, 16, 52, 43),
                        new ImageMetadata(null, null, null, "https://scx2.b-cdn.net/gfx/news/hires/2019/2-nature.jpg"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        UUID.randomUUID(),
                        "Image #2",
                        "",
                        LocalDateTime.of(2014, 8, 5, 10, 12, 24),
                        new ImageMetadata(null, null, null, "https://s3-us-west-2.amazonaws.com/uw-s3-cdn/wp-content/uploads/sites/6/2017/11/04133712/waterfall.jpg"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        UUID.randomUUID(),
                        "Image #3",
                        "",
                        LocalDateTime.of(2015, 5, 11, 15, 15, 45),
                        new ImageMetadata(null, null, null, "https://natgeo.imgix.net/factsheets/thumbnails/01-balance-of-nature.adapt.jpg?auto=compress,format&w=1600&h=900&fit=crop"),
                        new ClassificationResult(StatusEnum.ERROR)
                ),
                new Classification(
                        UUID.randomUUID(),
                        "Image #4",
                        "",
                        LocalDateTime.of(2016, 9, 6, 20, 32, 3),
                        new ImageMetadata(null, null, null, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRxEM1lQyU3UVMBT_FFnbavqyz8981Phc0-Aw&usqp=CAU"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        UUID.randomUUID(),
                        "Image #5",
                        "",
                        LocalDateTime.of(2017, 8, 12, 17, 32, 57),
                        new ImageMetadata(null, null, null, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT_m1rPy1Bj7Kvx4o6b_s_gBTbYW8xnqfMAgg&usqp=CAU"),
                        new ClassificationResult(StatusEnum.PENDING)
                ),
                new Classification(
                        UUID.randomUUID(),
                        "Image #6",
                        "",
                        LocalDateTime.of(2013, 10, 4, 16, 52, 43),
                        new ImageMetadata(null, null, null, "https://scx2.b-cdn.net/gfx/news/hires/2019/2-nature.jpg"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        UUID.randomUUID(),
                        "Image #7",
                        "",
                        LocalDateTime.of(2014, 8, 5, 10, 12, 24),
                        new ImageMetadata(null, null, null, "https://s3-us-west-2.amazonaws.com/uw-s3-cdn/wp-content/uploads/sites/6/2017/11/04133712/waterfall.jpg"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        UUID.randomUUID(),
                        "Image #8",
                        "",
                        LocalDateTime.of(2015, 5, 11, 15, 15, 45),
                        new ImageMetadata(null, null, null, "https://natgeo.imgix.net/factsheets/thumbnails/01-balance-of-nature.adapt.jpg?auto=compress,format&w=1600&h=900&fit=crop"),
                        new ClassificationResult(StatusEnum.ERROR)
                ),
                new Classification(
                        UUID.randomUUID(),
                        "Image #9",
                        "",
                        LocalDateTime.of(2016, 9, 6, 20, 32, 3),
                        new ImageMetadata(null, null, null, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRxEM1lQyU3UVMBT_FFnbavqyz8981Phc0-Aw&usqp=CAU"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        UUID.randomUUID(),
                        "Image #10",
                        "",
                        LocalDateTime.of(2017, 8, 12, 17, 32, 57),
                        new ImageMetadata(null, null, null, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT_m1rPy1Bj7Kvx4o6b_s_gBTbYW8xnqfMAgg&usqp=CAU"),
                        new ClassificationResult(StatusEnum.PENDING)
                )
        );
    }

}
