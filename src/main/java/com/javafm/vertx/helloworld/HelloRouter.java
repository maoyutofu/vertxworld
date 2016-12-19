package com.javafm.vertx.helloworld;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

/**
 * Created by lemontea <36634584.qq.com> on 16-12-19.
 */
public class HelloRouter {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // 创建一个http服务
        HttpServer httpServer = vertx.createHttpServer();

        // 创建一个Router对象
        Router router = Router.router(vertx);

        // 配置一个/hello路径，对应的访问地址是http://localhost:8080/hello
        router.route("/hello").handler(routingContext -> {
            routingContext.response().putHeader("Content-Type", "text/html").
                    end("hello");
        });

        // 配置一个/router路径，对应的访问地址是 http://localhost:8080/router
        router.route("/router").handler(routingContext -> {
            routingContext.response().putHeader("Content-Type", "text/html").
                    end("router");
        });

        httpServer.requestHandler(router::accept).listen(8080);
    }
}
