package com.studg.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // Configure for your MSSQL instance
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=User_stats";
    private static final String USER = "sa";
    private static final String PASS = "12345";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            // Driver not found; caller can fall back to in-memory
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
