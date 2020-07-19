package org.example.be.router;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.SseElementType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.SseEventSink;

@Tag(name = "Notification API", description = "Endpoints regarding notifications")
@Path("/api/v1/notifications")
public interface NotificationRouter {

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Operation(summary = "Read image classification notifications")
    void read(@Context SseEventSink sseEventSink);

}
