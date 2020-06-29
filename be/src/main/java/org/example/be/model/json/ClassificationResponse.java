package org.example.be.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.be.model.entity.Classification;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Data
public class ClassificationResponse implements Serializable {

    private static final long serialVersionUID = -8874756750139294954L;

    @JsonProperty(value = "url")
    private final String url;

    @JsonProperty(value = "name")
    private final String name;

    @JsonProperty(value = "created_at")
    private final String createdAt;

    @JsonProperty(value = "status")
    private final String status;

    public ClassificationResponse(Classification classification) {
        this.url = classification.getImageMetadata().getUrl();
        this.name = classification.getName();
        this.createdAt = classification.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = classification.getClassificationResult().getStatus().name();
    }

}
