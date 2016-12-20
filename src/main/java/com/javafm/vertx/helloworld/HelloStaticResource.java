package com.javafm.vertx.helloworld;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Created by lemontea <36634584@qq.com> on 16-12-20.
 */
public class HelloStaticResource {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        // 使用StaticHandler来处理静态文件handler
        // 需要注意的是静态文件要放到webroot里面，即 resources/webroot
        router.route("/*").handler(StaticHandler.create());
        httpServer.requestHandler(router::accept).listen(8080);
    }
}
