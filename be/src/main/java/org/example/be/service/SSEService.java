package org.example.be.service;

import io.vertx.core.json.JsonObject;
import org.example.be.model.entity.Classification;
import org.example.be.model.entity.ClassificationResult;
import org.example.be.model.json.ClassificationResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.util.UUID;

@ApplicationScoped
public class SSEService {

    /**
     * @param sse
     * @param sseBroadcaster
     */
    public void produce(Sse sse,
                        SseBroadcaster sseBroadcaster,
                        ClassificationResponse response) {
        if (sseBroadcaster != null) {
            final OutboundSseEvent sseEvent = sse.newEventBuilder()
                    .id(response.getId().toString())
                    .name("CLASSIFICATION_RESULT")
                    .data(JsonObject.mapFrom(response))
                    .build();

            sseBroadcaster.broadcast(sseEvent);
        }
    }

    /**
     * @param sse
     * @param sseBroadcaster
     * @param sseEventSink
     * @return
     */
    public SseBroadcaster consume(Sse sse,
                                  SseBroadcaster sseBroadcaster,
                                  SseEventSink sseEventSink) {
        if (sseBroadcaster == null) {
            sseBroadcaster = sse.newBroadcaster();
        }

        sseBroadcaster.register(sseEventSink);

        return sseBroadcaster;
    }

}
