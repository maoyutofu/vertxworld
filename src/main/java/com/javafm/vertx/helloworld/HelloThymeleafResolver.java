package com.javafm.vertx.helloworld;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Created by lemontea <36634584@qq.com> on 16-12-20.
 */
public class HelloThymeleafResolver {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        ThymeleafTemplateEngine templateEngine = ThymeleafTemplateEngine.create();
        // 定时模板解析器,表示从类加载路径下找模板
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        // 设置模板的前缀，我们设置的是templates目录
        templateResolver.setPrefix("templates");
        // 设置后缀为.html文件
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateEngine.getThymeleafTemplateEngine().setTemplateResolver(templateResolver);
        router.route("/hello").handler(routingContext -> {
            routingContext.put("msg", "Hello ClassLoaderTemplateResolver!");
            // 这里render的第二个参数，就是模板文件名称，由于在Resolver里面设置了前缀和后缀这里就可以简写了
            templateEngine.render(routingContext, "/hello", res -> {
                if (res.succeeded()) {
                    routingContext.response().putHeader("Content-Type", "text/html").end(res.result());
                } else {
                    routingContext.fail(res.cause());
                }
            });

        });
        httpServer.requestHandler(router::accept).listen(8080);
    }
}
