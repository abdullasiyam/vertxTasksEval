package com.verticlesCommunication.verticlesCommunication.helpers;

import io.vertx.core.json.JsonObject;

public class DBConfig {
  final public static JsonObject CONFIG = new JsonObject()
    .put("url", "jdbc:mysql://localhost:3306/communicate_verticles")
    .put("driver_class", "com.mysql.cj.jdbc.Driver")
    .put("user", "root")
    .put("password", "");
}
