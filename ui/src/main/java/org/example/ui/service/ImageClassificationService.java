package org.example.ui.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.example.ui.model.ImageClassification;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "be")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/v1/image-classification")
public interface ImageClassificationService {

    @GET
    List<ImageClassification> readAll();

}
