package org.example.be.model.event.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ClassificationRequestPayload {

    @JsonProperty(value = "id")
    private final UUID id;

    @JsonProperty(value = "url")
    private final String url;

}
