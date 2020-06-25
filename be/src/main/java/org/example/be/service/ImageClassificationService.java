package org.example.be.service;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.example.be.model.json.ImageClassificationResponse;
import org.example.be.repository.ImageClassificationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ImageClassificationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ImageClassificationService.class);

    @Inject
    ImageClassificationRepository imageClassificationRepository;

    public List<ImageClassificationResponse> getImageClassifications() {
        LOGGER.info("getImageClassifications() method called");

        return imageClassificationRepository.readAll()
                .stream()
                .map(ImageClassificationResponse::new)
                .limit(5)
                .collect(Collectors.toList());
    }

}
