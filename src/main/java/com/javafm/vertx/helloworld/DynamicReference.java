package com.javafm.vertx.helloworld;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Created by lemontea <36634584@qq.com> on 16-12-20.
 */
public class DynamicReference {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        engine.getThymeleafTemplateEngine().setTemplateResolver(resolver);

        Router router = Router.router(vertx);

        // 配置动态文件访问路径
        router.route("/hello").handler(routingContext -> {
            routingContext.put("msg", "DynamicReference.");
            engine.render(routingContext, "/hello", res -> {
                if (res.succeeded()) {
                    routingContext.response().putHeader("Content-Type", "text/html").end(res.result());
                } else {
                    routingContext.fail(res.cause());
                }
            });
        });

        // 配置静态文件
        router.route("/*").handler(StaticHandler.create());

        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept).listen(8080);
    }
}
