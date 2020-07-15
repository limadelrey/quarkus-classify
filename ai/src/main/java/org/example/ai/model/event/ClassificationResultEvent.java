package org.example.ai.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ClassificationResultEvent {

    private static final String ID_FIELD = "id";
    private static final String STATUS_FIELD = "status";

    @JsonProperty(value = ID_FIELD)
    private String id;

    @JsonProperty(value = STATUS_FIELD)
    private StatusEnum status;

}
