package com.verticlesCommunication.verticlesCommunication.helpers;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class DatabaseInsertion {
  public static void insertToDB(Vertx vertx, JsonObject msg){

    // Create a JDBC client
    JDBCClient client = JDBCClient.createShared(vertx, DBConfig.CONFIG);

    // Use the client to establish a connection
    client.getConnection(connHandler -> {
      if (connHandler.succeeded()) {
        SQLConnection connection = connHandler.result();
        //System.out.println("Connected to MySQL database.");

        // Define your SQL insert statement
        String insertSql = "INSERT INTO messages (message_content, message_from, carried_by) VALUES (?, ?, ?)";

        // Extract values from the JSON object and insert into the database
        connection.updateWithParams(insertSql, new JsonArray()
          .add(msg.getString("message_content"))
          .add(msg.getString("message_from"))
          .add(msg.getString("carried_by")), insertResult -> {
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

  }
}
