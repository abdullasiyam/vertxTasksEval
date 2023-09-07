package com.verticlesCommunication.verticlesCommunication;

import io.vertx.core.*;

public class StarterVerticle {
  public static void main(String[] args) {
    VertxOptions options = new VertxOptions();
    Vertx vertx = Vertx.vertx(options);
    vertx.deployVerticle(new PublishVerticle());
    vertx.deployVerticle(new SubVerticle1());
    vertx.deployVerticle(SubVerticle2.class.getName(), new DeploymentOptions().setInstances(2));
    vertx.deployVerticle(SubVerticle3.class.getName(), new DeploymentOptions().setInstances(3));
  }
}
