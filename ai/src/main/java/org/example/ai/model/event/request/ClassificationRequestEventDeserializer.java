package org.example.ai.model.event.request;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ClassificationRequestEventDeserializer extends JsonbDeserializer<ClassificationRequestEvent> {

    public ClassificationRequestEventDeserializer() {
        super(ClassificationRequestEvent.class);
    }

}
