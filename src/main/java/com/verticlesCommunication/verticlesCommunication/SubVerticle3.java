package com.verticlesCommunication.verticlesCommunication;

import com.verticlesCommunication.verticlesCommunication.helpers.DatabaseInsertion;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class SubVerticle3 extends AbstractVerticle {
  public void start(final Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().<JsonObject>consumer(PublishVerticle.ADDRESS, msgInput -> {
      JsonObject msg = msgInput.body().put("carried_by", SubVerticle3.class.getSimpleName());

      DatabaseInsertion.insertToDB(vertx, msg);
    });
  }
}
