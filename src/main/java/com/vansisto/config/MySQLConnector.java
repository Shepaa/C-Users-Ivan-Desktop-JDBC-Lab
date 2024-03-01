package com.vansisto.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

    private static final String MYSQL_DRIVE = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "030892030892S";
    private static final String DATABASE = "shop";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimeZone=UTC";

    public static Connection getConnection() {
        try {
            Class.forName(MYSQL_DRIVE);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
