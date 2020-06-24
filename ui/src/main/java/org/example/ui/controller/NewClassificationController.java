package org.example.ui.controller;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/new-classification")
public class NewClassificationController {

    @ResourcePath("new-classification.html")
    Template template;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance newClassification() {
        return template.data(null);
    }

}
