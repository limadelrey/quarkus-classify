package org.example.be.router;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.be.model.json.ClassificationRequest;
import org.example.be.model.json.ClassificationResponse;
import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSink;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Tag(name = "Image classification API", description = "Endpoints regarding image classification")
@Path("/api/v1/image-classification/notifications")
public interface ClassificationResultRouter {

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Operation(summary = "Read image classification notifications")
    void read(@Context SseEventSink sseEventSink);

}
