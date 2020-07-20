package org.example.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.example.ai.model.entity.ClassificationStatusEnum;
import org.example.ai.model.entity.ClassificationTag;
import org.example.ai.model.event.reply.ClassificationReplyEvent;
import org.example.ai.model.event.request.ClassificationRequestEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Traced
@ApplicationScoped
public class ClassificationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HashMap<UUID, Instant> consumedEventsMap = new HashMap<>();

    @Inject
    ImageClassificationService imageClassificationInPythonService;

    /**
     * Consumes ClassificationRequestEvent from "classification-request" Kafka topic;
     * Performs classification using Python code;
     * Produces ClassificationReplyEvent to "classification-reply" Kafka topic.
     *
     * @param event Classification request event
     */
    @Blocking
    @Incoming("classification-request")
    @Outgoing("classification-reply")
    public ClassificationReplyEvent performClassification(String event) {
        LOGGER.info("performClassification() method called");

        try {
            // Deserialize event manually (automatic deserializer couldn't work because the event is consumed w/ trash characters)
            final ClassificationRequestEvent requestEvent = deserializeEvent(event);

            // Check if event was already consumed (in-memory validation)
            isConsumed(requestEvent.getId());

            // Perform image classification using Python code (Polyglot API)
            final List<ClassificationTag> tags = imageClassificationInPythonService.execute(requestEvent.getUrl())
                    .stream()
                    .limit(6)
                    .collect(Collectors.toList());

            // Create reply event
            final ClassificationReplyEvent replyEvent = buildClassificationReplyPayload(requestEvent.getId(), tags);

            return replyEvent;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * Manual deserialization of events coming
     * from "classification-request" Kafka topic
     *
     * @param requestEvent Request event (String)
     * @return ClassificationRequestEvent
     */
    private ClassificationRequestEvent deserializeEvent(String requestEvent) {
        final JsonNode eventPayload;

        try {
            final String unescapedEvent = requestEvent.substring(requestEvent.indexOf("{"), requestEvent.lastIndexOf("}") + 1);
            eventPayload = objectMapper.readTree(unescapedEvent);
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't deserialize request event", ex);
        }

        return new ClassificationRequestEvent(
                UUID.fromString(eventPayload.get(ClassificationRequestEvent.ID_FIELD).asText()),
                eventPayload.get(ClassificationRequestEvent.URL_FIELD).asText()
        );
    }

    /**
     * Checks if event was already consumed in order
     * to avoid duplicate event consumption
     *
     * @param id Classification ID
     */
    private void isConsumed(UUID id) {
        if (consumedEventsMap.containsKey(id)) {
            throw new RuntimeException("Event with id " + id + " was already consumed at " + consumedEventsMap.get(id));
        } else {
            consumedEventsMap.put(id, Instant.now());
        }
    }

    /**
     * Builds classification reply event based
     * on image classification result (success/error)
     *
     * @param id   Classification ID
     * @param tags Classification tags
     * @return ClassificationReplyEvent
     */
    private ClassificationReplyEvent buildClassificationReplyPayload(UUID id,
                                                                     List<ClassificationTag> tags) {
        final ClassificationReplyEvent event;

        if (tags == null || tags.isEmpty()) {
            event = new ClassificationReplyEvent(id, ClassificationStatusEnum.ERROR, Collections.emptyList());

        } else {
            event = new ClassificationReplyEvent(id, ClassificationStatusEnum.SUCCESS, tags);
        }

        return event;
    }

}
