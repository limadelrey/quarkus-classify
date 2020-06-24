package org.example.ui.controller;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.example.ui.service.ImageClassificationService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HomeController {

    @ResourcePath("index.html")
    Template template;

    @Inject
    ImageClassificationService imageClassificationService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return template.data("classifications", imageClassificationService.getImageClassifications());
    }

}
