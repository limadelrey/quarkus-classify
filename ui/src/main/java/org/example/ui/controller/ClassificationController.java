package org.example.ui.controller;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.ui.service.ClassificationService;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

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
    @Path("/new-classification")
    public Response add(MultipartFormDataInput input) {
        LOGGER.info("add() method called");

        classificationService.create(multipartFormDataService.getClassificationRequest(input));

        return Response
                .status(301)
                .location(URI.create("/"))
                .build();
    }

}
