package org.example.be.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.example.be.model.entity.Classification;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.example.be.model.json.ClassificationResponse.*;

@Data
@JsonPropertyOrder({ID_FIELD, NAME_FIELD, DESCRIPTION_FIELD, URL_FIELD, STATUS_FIELD, CREATED_AT_FIELD})
public class ClassificationResponse implements Serializable {

    private static final long serialVersionUID = -8874756750139294954L;

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String URL_FIELD = "url";
    public static final String STATUS_FIELD = "status";
    public static final String CREATED_AT_FIELD = "created_at";

    @JsonProperty(value = ID_FIELD)
    private final UUID id;

    @JsonProperty(value = NAME_FIELD)
    private final String name;

    @JsonProperty(value = DESCRIPTION_FIELD)
    private final String description;

    @JsonProperty(value = URL_FIELD)
    private final String url;

    @JsonProperty(value = STATUS_FIELD)
    private final String status;

    @JsonProperty(value = CREATED_AT_FIELD)
    private final String createdAt;

    public ClassificationResponse(Classification classification) {
        this.id = classification.getId();
        this.name = classification.getName();
        this.description = classification.getDescription();
        this.url = classification.getImageMetadata().getUrl();
        this.status = classification.getClassificationResult().getStatus().name();
        this.createdAt = classification.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
