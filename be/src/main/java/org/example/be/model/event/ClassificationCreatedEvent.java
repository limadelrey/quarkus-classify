package org.example.be.model.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.outbox.quarkus.ExportedEvent;
import org.example.be.model.entity.Classification;

import java.time.Instant;
import java.util.UUID;

public class ClassificationCreatedEvent implements ExportedEvent<UUID, JsonNode> {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String AGGREGATE_TYPE = "Classification";
    private static final String EVENT_TYPE = "ClassificationCreated";

    private final UUID id;
    private final JsonNode payload;
    private final Instant timestamp;

    private ClassificationCreatedEvent(UUID id,
                                       JsonNode payload) {
        this.id = id;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public static ClassificationCreatedEvent of(Classification classification) {
        return new ClassificationCreatedEvent(classification.getId(), mapper.valueToTree(classification));
    }

    @Override
    public UUID getAggregateId() {
        return id;
    }

    @Override
    public String getAggregateType() {
        return AGGREGATE_TYPE;
    }

    @Override
    public String getType() {
        return EVENT_TYPE;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public JsonNode getPayload() {
        return payload;
    }

}
