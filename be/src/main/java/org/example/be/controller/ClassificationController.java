package org.example.be.controller;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.example.be.model.json.ClassificationRequest;
import org.example.be.router.ClassificationRouter;
import org.example.be.service.ClassificationService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.net.URI;

@ApplicationScoped
public class ClassificationController implements ClassificationRouter {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationController.class);

    @Inject
    ClassificationService classificationService;

    @Counted(name = "readAllCount", description = "How many readAll() calls have been performed")
    @Timed(name = "readAllTime", description = "How long readAll() call takes to perform", unit = MetricUnits.MILLISECONDS)
    public Response readAll() {
        LOGGER.info("readAll() method called");

        return Response
                .ok(classificationService.getAll())
                .build();
    }

    @Counted(name = "readOneCount", description = "How many readOne() calls have been performed")
    @Timed(name = "readOneTime", description = "How long readOne() call takes to perform", unit = MetricUnits.MILLISECONDS)
    public Response readOne(Long id) {
        LOGGER.info("readOne() method called");

        return Response
                .ok(classificationService.getById(id))
                .build();
    }

    @Counted(name = "createCount", description = "How many create() calls have been performed")
    @Timed(name = "createTime", description = "How long create() call takes to perform", unit = MetricUnits.MILLISECONDS)
    public Response create(@Valid ClassificationRequest request) {
        LOGGER.info("create() method called");

        final Long id = classificationService.insert(request);

        return Response
                .created(URI.create("/api/v1/image-classification/" + id))
                .build();
    }

    @Counted(name = "deleteCount", description = "How many delete() calls have been performed")
    @Timed(name = "deleteTime", description = "How long delete() call takes to perform", unit = MetricUnits.MILLISECONDS)
    public Response delete(Long id) {
        LOGGER.info("delete() method called");

        classificationService.delete(id);

        return Response
                .noContent()
                .build();
    }

}
