package org.example.ai.model.event.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

import java.util.UUID;

@Data
@RegisterForReflection
public class ClassificationRequestEvent {

    public static final String ID_FIELD = "id";
    public static final String URL_FIELD = "url";

    @JsonProperty(value = ID_FIELD)
    private final UUID id;

    @JsonProperty(value = URL_FIELD)
    private final String url;

}
