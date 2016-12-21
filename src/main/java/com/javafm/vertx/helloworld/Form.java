package com.javafm.vertx.helloworld;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Created by lemontea <36634584@qq.com> on 16-12-21.
 */
public class Form {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        engine.getThymeleafTemplateEngine().setTemplateResolver(resolver);

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.get("/user").handler(routingContext -> {
            engine.render(routingContext, "/user", res -> {
                if (res.succeeded()) {
                    routingContext.response().putHeader("Content-Type", "text/html").end(res.result());
                } else {
                    routingContext.fail(res.cause());
                }
            });
        });

        router.post("/user").handler(routingContext -> {
            String name = routingContext.request().getParam("name");
            String sex = routingContext.request().getParam("sex");
            String age = routingContext.request().getParam("age");
            routingContext.response().putHeader("Content-Type", "text/html").end("姓名：" + name + "，性别：" + sex + "，年龄：" + age);
        });


        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept).listen(8080);
    }
}
