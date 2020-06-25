package org.example.be.router;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.be.model.json.ImageClassificationResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Tag(name = "Books API", description = "Endpoints regarding image classification")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/api/v1/image-classification")
public interface ImageClassificationRouter {

    @GET
    @Operation(summary = "Read all image classifications")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    name = "success",
                    description = "Success",
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(
                                    implementation = ImageClassificationResponse.class,
                                    type = SchemaType.ARRAY
                            )
                    )
            ),
            @APIResponse(
                    responseCode = "500",
                    name = "timeout",
                    description = "Internal Server Error"
            )
    })
    Response readAll();

}
