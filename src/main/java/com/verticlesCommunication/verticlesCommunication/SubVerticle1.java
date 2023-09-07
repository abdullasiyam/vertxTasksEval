package com.verticlesCommunication.verticlesCommunication;

import com.verticlesCommunication.verticlesCommunication.helpers.DBConfig;
import com.verticlesCommunication.verticlesCommunication.helpers.DatabaseInsertion;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class SubVerticle1 extends AbstractVerticle {
  public void start(final Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().<JsonObject>consumer(PublishVerticle.ADDRESS, message -> {
      JsonObject msg = message.body().put("carried_by", SubVerticle1.class.getSimpleName());

      DatabaseInsertion.insertToDB(vertx, msg);
    });
  }
}
