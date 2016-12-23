package com.javafm.vertx.helloworld;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

/**
 * Created by lemontea <36634584@qq.com> on 16-12-23.
 */
public class DataAccess {
    private static DataAccess dataAccess;
    private static JDBCClient jdbcClient;
    private static JsonObject config;

    static {
        config = new JsonObject();
        config.put("url", "jdbc:mysql://112.74.78.111:3306/test");
        config.put("driver_class", "com.mysql.jdbc.Driver");
        config.put("user", "root");
        config.put("password", "admin1001");
    }

    public static DataAccess create(Vertx vertx) {
        if (dataAccess == null) {
            synchronized (DataAccess.class) {
                if (dataAccess == null) {
                    dataAccess = new DataAccess();
                    dataAccess.init(vertx);
                }
            }
        }
        return dataAccess;
    }

    private void init(Vertx vertx) {
        jdbcClient = JDBCClient.createShared(vertx, config);
    }

    public JDBCClient getJDBCClient() {
        return jdbcClient;
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        DataAccess dataAccess = DataAccess.create(vertx);
        dataAccess.getJDBCClient().getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.query("SELECT * FROM `test`", res2 -> {
                    ResultSet rs = res2.result();
                    rs.getResults().forEach(e -> {
                        System.out.println(e.getInteger(0) + " " + e.getString(1));
                    });
                    conn.close();
                });
            } else {
                System.out.println(res.cause());
            }
        });
    }
}
