package com.javafm.vertx.helloworld;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;

/**
 * Created by lemontea <36634584@qq.com> on 16-12-19.
 */
public class HelloThymeleaf {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        // 创建一个模板引擎
        ThymeleafTemplateEngine templateEngine = ThymeleafTemplateEngine.create();
        // 设置访问路径
        router.route("/hello").handler(routingContext -> {
            // 传递值到模板中，在模板中可以通过${msg}直接取出
           routingContext.put("msg", "Hello Thymeleaf!");
            // 渲染模板
            templateEngine.render(routingContext, "templates/hello.html", res -> {
                // 如果模板解析成功，就将结果写到response
                if (res.succeeded()) {
                    routingContext.response().putHeader("Content-Type", "text/html").end(res.result());
                } else {  // 如果解析失败，就显示fail
                    routingContext.fail(res.cause());
                }
            });
        });
        httpServer.requestHandler(router::accept).listen(8080);
    }
}
