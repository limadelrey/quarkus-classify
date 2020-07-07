package org.example.be.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.be.model.entity.StatusEnum;

import java.util.UUID;

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
