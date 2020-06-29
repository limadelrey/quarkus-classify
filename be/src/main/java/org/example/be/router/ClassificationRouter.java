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

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Tag(name = "BE API", description = "Endpoints regarding image classification")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/api/v1/image-classification")
public interface ClassificationRouter {

    @GET
    @Operation(summary = "Read all image classifications")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    name = "success",
                    description = "Success",
                    content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ClassificationResponse.class, type = SchemaType.ARRAY))
            ),
            @APIResponse(
                    responseCode = "500",
                    name = "timeout",
                    description = "Internal Server Error"
            )
    })
    Response readAll();

    @GET
    @Path("{id}")
    @Operation(summary = "Read one image classification")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    name = "success",
                    description = "Success",
                    content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ClassificationResponse.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    name = "error",
                    description = "Bad request"
            ),
            @APIResponse(
                    responseCode = "404",
                    name = "not found",
                    description = "Image classification not found"
            ),
            @APIResponse(
                    responseCode = "500",
                    name = "timeout",
                    description = "Internal Server Error"
            )
    })
    Response readOne(@PathParam("id") Long id);

    @POST
    @Operation(summary = "Create image classification")
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    name = "success",
                    description = "Image classification created"
            ),
            @APIResponse(
                    responseCode = "400",
                    name = "invalid",
                    description = "Image classification is invalid"
            ),
            @APIResponse(
                    responseCode = "500",
                    name = "internalError",
                    description = "Internal Server Error"
            )
    })
    Response create(@RequestBody(
            description = "Image classification request",
            required = true,
            content = @Content(schema = @Schema(implementation = ClassificationRequest.class))) @Valid ClassificationRequest request);

}
