package org.example.be.model.event.reply;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ClassificationReplyEvent {

    private static final String ID_FIELD = "id";
    private static final String STATUS_FIELD = "status";
    private static final String TAGS_FIELD = "tags";

    @JsonProperty(value = ID_FIELD)
    private String id;

    @JsonProperty(value = STATUS_FIELD)
    private ClassificationStatusEnum status;

    @JsonProperty(value = TAGS_FIELD)
    private List<ClassificationTags> tags;

    @Data
    public class ClassificationTags {

        private static final String NAME_FIELD = "name";
        private static final String CONFIDENCE_FIELD = "confidence";

        @JsonProperty(value = NAME_FIELD)
        private String name;

        @JsonProperty(value = CONFIDENCE_FIELD)
        private Double confidence;
    }

}
