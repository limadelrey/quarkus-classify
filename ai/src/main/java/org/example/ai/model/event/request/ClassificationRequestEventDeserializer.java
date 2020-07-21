package org.example.ai.model.event.request;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ClassificationRequestEventDeserializer extends ObjectMapperDeserializer<ClassificationRequestEvent> {

    public ClassificationRequestEventDeserializer() {
        super(ClassificationRequestEvent.class);
    }

}
