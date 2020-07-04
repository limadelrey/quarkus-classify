package org.example.be.service;

import io.quarkus.panache.common.Sort;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.example.be.model.entity.Classification;
import org.example.be.model.entity.ClassificationResult;
import org.example.be.model.entity.ImageMetadata;
import org.example.be.model.entity.StatusEnum;
import org.example.be.model.json.ClassificationRequest;
import org.example.be.model.json.ClassificationResponse;
import org.example.be.repository.ClassificationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClassificationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationService.class);

    @Inject
    S3Service s3Service;

    @Inject
    ClassificationRepository classificationRepository;

    public List<ClassificationResponse> getAll() {
        LOGGER.info("getAll() method called");

        return classificationRepository
                .streamAll(Sort.descending(Classification.CREATED_AT_FIELD))
                .filter(classification -> classification.getClassificationResult().getStatus() != StatusEnum.CANCELED)
                .map(ClassificationResponse::new)
                .limit(5)
                .collect(Collectors.toList());

        /*return getStaticListWithClassifications()
                .stream()
                .map(ClassificationResponse::new)
                .collect(Collectors.toList());*/
    }

    public ClassificationResponse getById(UUID id) {
        LOGGER.info("getById() method called");

        final Classification classification = classificationRepository.findById(id);

        if (classification == null) {
            throw new NoSuchElementException("No image classification with id " + id);
        }

        return new ClassificationResponse(classification);
    }

    @Transactional
    public UUID insert(ClassificationRequest request) {
        LOGGER.info("insert() method called");

        final UUID id = UUID.randomUUID();

        // Upload to S3 bucket
        final String url = s3Service.upload(id, request.getFile(), request.getMimeType());

        // Save metadata on database
        saveMetadataOnDatabase(request, id, url);

        // Publish outbox event

        return id;
    }

    @Transactional
    public void delete(UUID id) {
        final Classification classification = classificationRepository.findById(id);

        if (classification == null) {
            throw new NoSuchElementException("No image classification with id " + id);
        }

        classification.getClassificationResult().setStatus(StatusEnum.CANCELED);

        classificationRepository.persist(classification);
    }

    //

    private void saveMetadataOnDatabase(ClassificationRequest request,
                                        UUID id,
                                        String url) {
        // Create image metadata entity
        final ImageMetadata imageMetadata = new ImageMetadata(request.getFileName(), getSizeFromFile(request.getFile()), request.getMimeType(), url);

        // Create classification result entity
        final ClassificationResult classificationResult = new ClassificationResult(StatusEnum.PENDING);

        // Create classification entity
        final Classification classification = new Classification(id, request.getClassificationName(), request.getClassificationDescription(), LocalDateTime.now(), imageMetadata, classificationResult);

        classificationRepository.persist(classification);
    }

    private Long getSizeFromFile(InputStream inputStream) {
        try {
            return Long.valueOf(inputStream.readAllBytes().length);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

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
