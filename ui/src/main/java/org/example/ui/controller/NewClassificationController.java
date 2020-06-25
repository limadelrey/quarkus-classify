package org.example.ui.controller;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/new-classification")
public class NewClassificationController {

    private final Logger LOGGER = LoggerFactory.getLogger(NewClassificationController.class);

    @ResourcePath("new-classification.html")
    Template template;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Counted(name = "newClassificationCount", description = "How many render() calls have been performed")
    @Timed(name = "newClassificationTime", description = "How long render() call takes to perform", unit = MetricUnits.MILLISECONDS)
    public TemplateInstance render() {
        LOGGER.info("render() method called");

        return template.data(null);
    }

}
