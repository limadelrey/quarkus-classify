package org.example.be.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.be.model.entity.Classification;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Data
public class ClassificationResponse implements Serializable {

    private static final long serialVersionUID = -8874756750139294954L;

    @JsonProperty(value = "id")
    private final Long id;

    @JsonProperty(value = "name")
    private final String name;

    @JsonProperty(value = "description")
    private final String description;

    @JsonProperty(value = "created_at")
    private final String createdAt;

    @JsonProperty(value = "status")
    private final String status;

    @JsonProperty(value = "url")
    private final String url;

    public ClassificationResponse(Classification classification) {
        this.id = classification.getId();
        this.name = classification.getName();
        this.description = classification.getDescription();
        this.createdAt = classification.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = classification.getClassificationResult().getStatus().name();
        this.url = classification.getImageMetadata().getUrl();
    }

}
