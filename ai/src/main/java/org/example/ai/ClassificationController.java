package org.example.ai;

import org.example.ai.service.ClassificationService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/ai")
public class ClassificationController {

    @Inject
    ClassificationService classificationService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello(String url) {
        return Response
                .ok(classificationService.performClassification(url))
                .build();
    }

}
