package com.verticlesCommunication.verticlesCommunication;

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
      JsonObject messageVert = message.body().put("carried_by", SubVerticle1.class.getSimpleName());

      // Configure the database connection
      JsonObject config = new JsonObject()
        .put("url", "jdbc:mysql://localhost:3306/communicate_verticles")
        .put("driver_class", "com.mysql.cj.jdbc.Driver")
        .put("user", "root")
        .put("password", "");

      // Create a JDBC client
      JDBCClient client = JDBCClient.createShared(vertx, config);

      // Use the client to establish a connection
      client.getConnection(connHandler -> {
        if (connHandler.succeeded()) {
          SQLConnection connection = connHandler.result();
          //System.out.println("Connected to MySQL database.");

          // Define your SQL insert statement
          String insertSql = "INSERT INTO messages (message_content, message_from, carried_by) VALUES (?, ?, ?)";

          // Extract values from the JSON object and insert into the database
          connection.updateWithParams(insertSql, new JsonArray()
            .add(messageVert.getString("message_content"))
            .add(messageVert.getString("message_from"))
            .add(messageVert.getString("carried_by")), insertResult -> {
            if (insertResult.succeeded()) {
              System.out.println("Data inserted successfully.");
            } else {
              System.err.println("Failed to insert data: " + insertResult.cause().getMessage());
            }
            connection.close();
          });

        } else {
          System.err.println("Failed to connect to MySQL database: " + connHandler.cause().getMessage());
        }
      });
    });
  }
}
