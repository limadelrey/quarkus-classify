package org.example.be.service;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.example.be.model.event.ClassificationResultEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClassificationResultService {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassificationResultService.class);

    @Inject
    EventBus bus;

    @Incoming("classification")
//    @Outgoing("my-stream")
//    @Broadcast
    public void process(ClassificationResultEvent event) {
        System.out.println("TESTE 1");
        bus.sendAndForget("my-stream", JsonObject.mapFrom(event));
    }

    /*@Incoming("my-stream")
    public void teste(ClassificationResultEvent event) {
        System.out.println("TESTE SUCCESS");
        classificationService.update(event.getId(), new ClassificationResult(event.getStatus()));
        // sseService.produce();
    }*/

}
