package org.example.ui.controller;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.ui.service.ClassificationService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @ResourcePath(value = "index.html")
    Template template;

    @Inject
    @RestClient
    ClassificationService classificationService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Counted(name = "indexCount", description = "How many render() calls have been performed")
    @Timed(name = "indexTime", description = "How long render() call takes to perform", unit = MetricUnits.MILLISECONDS)
    public TemplateInstance renderMainPage() {
        LOGGER.info("renderMainPage() method called");

        return template.data("classifications", classificationService.readAll());
    }

}
