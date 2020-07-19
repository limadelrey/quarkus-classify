package org.example.be.service;

import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.opentracing.Traced;
import org.example.be.model.json.ClassificationGetAllResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@Traced
@ApplicationScoped
public class SSEService {

    /**
     * Publish an SSE event to all registered SseEventSink instances.
     *
     * @param sse            SSE API
     * @param sseBroadcaster SSE broadcasting facility
     * @param response       Classification response
     */
    public void produce(Sse sse,
                        SseBroadcaster sseBroadcaster,
                        ClassificationGetAllResponse response) {
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
     * Register SseEventSink instance to SseBroadcaster allowing it to consume events over time.
     *
     * @param sse            SSE API
     * @param sseBroadcaster SSE broadcasting facility
     * @param sseEventSink   Outbound SSE stream
     * @return SseBroadcaster
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
