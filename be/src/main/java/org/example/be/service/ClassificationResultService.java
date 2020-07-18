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
public class ClassificationResultService {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationResultService.class);

    @Inject
    EventBus bus;

    /**
     * Consumes data from "classification-result" Kafka topic.
     * Event is then sent to event bus through "update-classification-result" address.
     *
     * @param event Classification result event
     */
    @Incoming("classification")
    public void consume(ClassificationReplyEvent event) {
        LOGGER.info("consume() method called");
        LOGGER.info(event.toString());

        bus.sendAndForget("update-classification-result", event);
    }

}
