package org.example.ai.service;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/ai")
public class ClassificationController {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationController.class);

    @Inject
    ImageClassificationService classificationService;

    @GET
    public Response readAll() {
        LOGGER.info("readAll() method called");

        return Response
                .ok(classificationService.execute())
                .build();
    }

}
