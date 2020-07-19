package org.example.be.model.event.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ClassificationRequestPayload {

    private static final String ID_FIELD = "id";
    private static final String URL_FIELD = "url";

    @JsonProperty(value = ID_FIELD)
    private final UUID id;

    @JsonProperty(value = URL_FIELD)
    private final String url;

}
