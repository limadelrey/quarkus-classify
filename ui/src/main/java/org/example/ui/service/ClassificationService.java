package org.example.ui.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.example.ui.model.Classification;
import org.example.ui.model.ClassificationRequest;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RegisterRestClient(configKey = "be")
@Path(value = "/api/v1/image-classification")
public interface ClassificationService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    List<Classification> readAll();

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Classification readOne(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(@Valid @MultipartForm ClassificationRequest request);

}
