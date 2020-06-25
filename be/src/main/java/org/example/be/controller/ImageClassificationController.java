package org.example.be.controller;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.example.be.router.ImageClassificationRouter;
import org.example.be.service.ImageClassificationService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class ImageClassificationController implements ImageClassificationRouter {

    private final Logger LOGGER = LoggerFactory.getLogger(ImageClassificationController.class);

    @Inject
    ImageClassificationService imageClassificationService;

    @Counted(name = "readAllCount", description = "How many readAll() calls have been performed")
    @Timed(name = "readAllTime", description = "How long readAll() call takes to perform", unit = MetricUnits.MILLISECONDS)
    public Response readAll() {
        LOGGER.info("readAll() method called");

        return Response.ok(imageClassificationService.getImageClassifications()).build();
    }

}
