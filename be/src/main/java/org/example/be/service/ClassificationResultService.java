package org.example.be.service;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.example.be.model.event.ClassificationResultEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.NoSuchElementException;

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
    public void consume(ClassificationResultEvent event) {
        LOGGER.info("consume() method called");

        bus.sendAndForget("update-classification-result", event);
    }

}
