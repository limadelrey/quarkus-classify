package org.example.ui.model.json;

import lombok.Data;
import org.example.ui.model.entity.ImageClassification;

import java.time.format.DateTimeFormatter;

@Data
public class ImageClassificationResponse {

    private final String url;
    private final String name;
    private final String createdAt;
    private final String status;

    public ImageClassificationResponse(ImageClassification classification) {
        this.url = classification.getUrl();
        this.name = classification.getName();
        this.createdAt = classification.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = classification.getStatus().name();
    }

}
