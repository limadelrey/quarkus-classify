package org.example.be.service;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.example.be.model.entity.ClassificationResult;
import org.example.be.model.entity.StatusEnum;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class ConsumerTestService {

    @Inject
    ClassificationService classificationService;

    @Inject
    EventBus bus;

    @ConsumeEvent(value = "my-stream", blocking = true)
    public void consume(Message<JsonObject> message) {
        System.out.println("TESTE 3");

        UUID id = UUID.fromString(message.body().getString("id"));
        StatusEnum status = StatusEnum.valueOf(message.body().getString("status"));

        try {
            classificationService.update(id, new ClassificationResult(status));
        } catch (Exception ex) {
            System.out.println("TESTE 4");
            bus.sendAndForget("my-stream-2", true);
        }
    }

}
