package org.example.be.model.event.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.time.Instant;
import java.util.UUID;

public class ClassificationRequestEvent implements ExportedEvent<UUID, JsonNode> {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String AGGREGATE_TYPE = "Classification";
    private static final String EVENT_TYPE = "ClassificationCreated";

    private final UUID id;
    private final JsonNode payload;
    private final Instant timestamp;

    private ClassificationRequestEvent(UUID id,
                                       JsonNode payload) {
        this.id = id;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public static ClassificationRequestEvent of(UUID id,
                                                String url) {
        return new ClassificationRequestEvent(id, mapper.valueToTree(new ClassificationRequestPayload(id, url)));
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
