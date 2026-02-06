package com.learningplatform.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // MSSQL Connection Parameters - Modify with your database details
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=LearningPlatform;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("MSSQL JDBC Driver not found: " + e.getMessage());
        }
    }

    /**
     * Get database connection
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Close connection safely
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Test database connection
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                closeConnection(conn);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Connection test failed: " + e.getMessage());
        }
        return false;
    }
}
