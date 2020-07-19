package org.example.be.service;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.example.be.model.event.reply.ClassificationReplyEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Traced
@ApplicationScoped
public class ClassificationReplyService {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationReplyService.class);

    @Inject
    EventBus bus;

    /**
     * Consumes data from "classification-reply" Kafka topic.
     * Event is then sent to event bus through "update-classification" address.
     *
     * @param event Classification reply event
     */
    @Incoming("classification-reply")
    public void consume(ClassificationReplyEvent event) {
        LOGGER.info("consume() method called");
        LOGGER.info(event.toString());

        bus.sendAndForget("update-classification", event);
    }

}
