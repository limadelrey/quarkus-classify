package org.example.be.model.event.reply;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ClassificationReplyEventDeserializer extends ObjectMapperDeserializer<ClassificationReplyEvent> {

    public ClassificationReplyEventDeserializer() {
        super(ClassificationReplyEvent.class);
    }

}
