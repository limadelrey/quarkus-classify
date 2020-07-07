package org.example.be.controller;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.example.be.model.entity.Classification;
import org.example.be.model.json.ClassificationResponse;
import org.example.be.router.ClassificationResultRouter;
import org.example.be.service.SSEService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@ApplicationScoped
public class ClassificationResultController implements ClassificationResultRouter {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationResultController.class);

    private Sse sse;
    private SseBroadcaster sseBroadcaster = null;

    @Inject
    SSEService sseService;

    @Context
    public void setSse(Sse sse) {
        this.sse = sse;
    }

    /**
     * Consumes data from "produce-sse-notification" address on event bus.
     * Event is then used to produce a sse notification.
     *
     * @param message Message w/ classification
     */
    @ConsumeEvent(value = "produce-sse-notification")
    public void create(Message<ClassificationResponse> message) {
        LOGGER.info("create() method called");

        sseService.produce(sse, sseBroadcaster, message.body());
    }

    @Counted(name = "readCount", description = "How many read() calls have been performed")
    @Timed(name = "readTime", description = "How long read() call takes to perform", unit = MetricUnits.MILLISECONDS)
    public void read(@Context SseEventSink sseEventSink) {
        LOGGER.info("read() method called");

        sseBroadcaster = sseService.consume(sse, sseBroadcaster, sseEventSink);
    }

}
