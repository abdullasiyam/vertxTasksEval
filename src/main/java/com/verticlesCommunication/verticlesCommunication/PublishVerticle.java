package com.verticlesCommunication.verticlesCommunication;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class PublishVerticle extends AbstractVerticle {
  static final String ADDRESS = "my.address";

  public void start(final Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);

    // Body handler for parsing request bodies
    router.route().handler(BodyHandler.create());

    // POST endpoint for creating new items
    router.post("/egypt/messages").handler(rc -> {
      JsonObject message = rc.getBodyAsJson();
      JsonObject msgInput = message.put("message_from", PublishVerticle.class.getSimpleName());

      // Send the item to the other verticle
      EventBus eventBus = vertx.eventBus();
      eventBus.send(ADDRESS, msgInput);

      rc.response().end();
    });

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
  }
