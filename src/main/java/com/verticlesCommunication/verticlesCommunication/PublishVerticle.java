package com.verticlesCommunication.verticlesCommunication;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import java.time.Duration;

public class PublishVerticle extends AbstractVerticle {
    static final String ADDRESS = "my.address";

    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      EventBus eventBus = vertx.eventBus();
      final JsonObject message = new JsonObject()
        .put("message_content", "Sa7 Edda7 Emboo")
        .put("message_from", PublishVerticle.class.getSimpleName());
      vertx.setPeriodic(Duration.ofSeconds(10).toMillis(), id -> {
        eventBus.<JsonObject>publish(ADDRESS, message);
      });
    }
  }
