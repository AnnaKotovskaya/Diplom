package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlHelper {

    private static final String DB_URL = System.getProperty("datasource.url");

    private SqlHelper() {
    }

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(DB_URL, "app", "pass");
    }


    @SneakyThrows
    public static void clearDataBase() {
        QueryRunner runner = new QueryRunner();
        try (var connection = getConnection()) {
            runner.execute(connection, "DELETE FROM credit_request_entity");
            runner.execute(connection, "DELETE FROM order_entity");
            runner.execute(connection, "DELETE FROM payment_entity");
        }
    }


    @SneakyThrows
    public static String getPaymentStatus() {
        QueryRunner runner = new QueryRunner();
        var sqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var connection = getConnection();
        return runner.query(connection, sqlStatus, new ScalarHandler<String>());
    }


    @SneakyThrows
    public static String getPaymentCreditStatus() {
        QueryRunner runner = new QueryRunner();
        var sqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        var connection = getConnection();
        return runner.query(connection, sqlStatus, new ScalarHandler<String>());
    }

}
