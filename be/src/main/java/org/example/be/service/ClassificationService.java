package org.example.be.service;

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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClassificationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationService.class);

    @Inject
    ClassificationRepository classificationRepository;

    public List<ClassificationResponse> getAll() {
        LOGGER.info("getAll() method called");

        return getStaticListWithClassifications()
                .stream()
                .map(ClassificationResponse::new)
                .limit(5)
                .collect(Collectors.toList());
    }

    public ClassificationResponse getById(Long id) {
        final Classification classification = classificationRepository.findById(id);

        return new ClassificationResponse(classification);
    }

    @Transactional
    public Long insert(ClassificationRequest request) {
        // Read file

        // Extract metadata from file

        // Create image metadata entity
        final ImageMetadata imageMetadata = new ImageMetadata(null, null, null, null);

        // Create classification result entity
        final ClassificationResult classificationResult = new ClassificationResult(StatusEnum.PENDING);

        // Create classification entity
        final Classification classification = new Classification(request.getName(), LocalDateTime.now(), imageMetadata, classificationResult);

        classificationRepository.persist(classification);

        return classification.getId();
    }

    private List<Classification> getStaticListWithClassifications() {
        return List.of(
                new Classification(
                        "Image #1",
                        LocalDateTime.of(2013, 10, 4, 16, 52, 43),
                        new ImageMetadata(null, null, null, "https://scx2.b-cdn.net/gfx/news/hires/2019/2-nature.jpg"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        "Image #2",
                        LocalDateTime.of(2014, 8, 5, 10, 12, 24),
                        new ImageMetadata(null, null, null, "https://s3-us-west-2.amazonaws.com/uw-s3-cdn/wp-content/uploads/sites/6/2017/11/04133712/waterfall.jpg"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        "Image #3",
                        LocalDateTime.of(2015, 5, 11, 15, 15, 45),
                        new ImageMetadata(null, null, null, "https://natgeo.imgix.net/factsheets/thumbnails/01-balance-of-nature.adapt.jpg?auto=compress,format&w=1600&h=900&fit=crop"),
                        new ClassificationResult(StatusEnum.ERROR)
                ),
                new Classification(
                        "Image #4",
                        LocalDateTime.of(2016, 9, 6, 20, 32, 3),
                        new ImageMetadata(null, null, null, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRxEM1lQyU3UVMBT_FFnbavqyz8981Phc0-Aw&usqp=CAU"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        "Image #5",
                        LocalDateTime.of(2017, 8, 12, 17, 32, 57),
                        new ImageMetadata(null, null, null, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT_m1rPy1Bj7Kvx4o6b_s_gBTbYW8xnqfMAgg&usqp=CAU"),
                        new ClassificationResult(StatusEnum.PENDING)
                ),
                new Classification(
                        "Image #6",
                        LocalDateTime.of(2013, 10, 4, 16, 52, 43),
                        new ImageMetadata(null, null, null, "https://scx2.b-cdn.net/gfx/news/hires/2019/2-nature.jpg"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        "Image #7",
                        LocalDateTime.of(2014, 8, 5, 10, 12, 24),
                        new ImageMetadata(null, null, null, "https://s3-us-west-2.amazonaws.com/uw-s3-cdn/wp-content/uploads/sites/6/2017/11/04133712/waterfall.jpg"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        "Image #8",
                        LocalDateTime.of(2015, 5, 11, 15, 15, 45),
                        new ImageMetadata(null, null, null, "https://natgeo.imgix.net/factsheets/thumbnails/01-balance-of-nature.adapt.jpg?auto=compress,format&w=1600&h=900&fit=crop"),
                        new ClassificationResult(StatusEnum.ERROR)
                ),
                new Classification(
                        "Image #9",
                        LocalDateTime.of(2016, 9, 6, 20, 32, 3),
                        new ImageMetadata(null, null, null, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRxEM1lQyU3UVMBT_FFnbavqyz8981Phc0-Aw&usqp=CAU"),
                        new ClassificationResult(StatusEnum.ACTIVE)
                ),
                new Classification(
                        "Image #10",
                        LocalDateTime.of(2017, 8, 12, 17, 32, 57),
                        new ImageMetadata(null, null, null, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT_m1rPy1Bj7Kvx4o6b_s_gBTbYW8xnqfMAgg&usqp=CAU"),
                        new ClassificationResult(StatusEnum.PENDING)
                )
        );
    }

}
