package com.javafm.vertx.helloworld;

import io.vertx.core.Vertx;

/**
 * Created by lemontea <36634584.qq.com> on 16-12-19.
 */
public class HelloWorld {
    public static void main(String[] args) {
        Vertx.vertx().createHttpServer().requestHandler(request -> {
            request.response().putHeader("Content-Type", "text/html").
                    end("Hello World");
        }).listen(8080);
    }
}
