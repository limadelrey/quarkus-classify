package org.example.be.model.event;

import lombok.Data;
import org.example.be.model.entity.StatusEnum;

import java.util.UUID;

@Data
public class ClassificationResultEvent {

    private UUID id;
    private StatusEnum status;

}
