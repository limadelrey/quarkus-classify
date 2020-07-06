package org.example.be.model.event;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ClassificationResultEventDeserializer extends ObjectMapperDeserializer<ClassificationResultEvent> {

    public ClassificationResultEventDeserializer() {
        super(ClassificationResultEvent.class);
    }

}
