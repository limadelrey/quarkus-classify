package org.example.ai.model.event.reply;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.ai.model.entity.ClassificationStatusEnum;
import org.example.ai.model.entity.ClassificationTag;

import java.util.List;
import java.util.UUID;

@Data
public class ClassificationReplyEvent {

    private static final String ID_FIELD = "id";
    private static final String STATUS_FIELD = "status";
    private static final String TAGS_FIELD = "tags";

    @JsonProperty(value = ID_FIELD)
    private final UUID id;

    @JsonProperty(value = STATUS_FIELD)
    private final ClassificationStatusEnum status;

    @JsonProperty(value = TAGS_FIELD)
    private final List<ClassificationTag> tags;

}
