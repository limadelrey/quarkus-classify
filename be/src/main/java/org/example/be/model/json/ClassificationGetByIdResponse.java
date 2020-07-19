package org.example.be.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.example.be.model.entity.Classification;
import org.example.be.model.entity.ClassificationTag;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.example.be.model.json.ClassificationGetByIdResponse.*;

@Data
@JsonPropertyOrder({ID_FIELD, NAME_FIELD, DESCRIPTION_FIELD, URL_FIELD, STATUS_FIELD, TAGS_FIELD, CREATED_AT_FIELD, UPDATED_AT_FIELD})
public class ClassificationGetByIdResponse implements Serializable {

    private static final long serialVersionUID = -8874756750139294954L;

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String URL_FIELD = "url";
    public static final String STATUS_FIELD = "status";
    public static final String TAGS_FIELD = "tags";
    public static final String CREATED_AT_FIELD = "created_at";
    public static final String UPDATED_AT_FIELD = "updated_at";

    @JsonProperty(value = ID_FIELD)
    private UUID id;

    @JsonProperty(value = NAME_FIELD)
    private String name;

    @JsonProperty(value = DESCRIPTION_FIELD)
    private String description;

    @JsonProperty(value = URL_FIELD)
    private String url;

    @JsonProperty(value = STATUS_FIELD)
    private String status;

    @JsonProperty(value = TAGS_FIELD)
    private List<ClassificationTag> tags;

    @JsonProperty(value = CREATED_AT_FIELD)
    private String createdAt;

    @JsonProperty(value = UPDATED_AT_FIELD)
    private String updatedAt;

    public ClassificationGetByIdResponse(Classification classification) {
        this.id = classification.getId();
        this.name = classification.getName();
        this.description = classification.getDescription();
        this.url = classification.getImageMetadata().getUrl();
        this.status = classification.getClassificationResult().getStatus().name();
        this.tags = classification.getClassificationResult().getTags();
        this.createdAt = classification.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updatedAt = (classification.getUpdatedAt() == null)
                ? null
                : classification.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
