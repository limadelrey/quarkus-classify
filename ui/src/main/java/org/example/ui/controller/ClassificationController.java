package org.example.ui.controller;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.ui.service.ClassificationService;
import org.example.ui.service.MultipartFormDataService;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;

@Path("/")
public class ClassificationController {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationController.class);

    @Inject
    @RestClient
    ClassificationService classificationService;

    @Inject
    MultipartFormDataService multipartFormDataService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("new-classification")
    public Response create(MultipartFormDataInput input) {
        LOGGER.info("add() method called");

        classificationService.create(multipartFormDataService.buildClassificationRequest(input));

        return Response
                .status(301)
                .location(URI.create("/"))
                .build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("read-classification/{id}")
    public Response read(@PathParam("id") UUID id) {
        LOGGER.info("read() method called");

        return classificationService.readOne(id);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("delete-classification/{id}")
    public Response delete(@PathParam("id") UUID id) {
        LOGGER.info("delete() method called");

        return classificationService.delete(id);
    }

}
