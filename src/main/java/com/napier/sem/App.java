
package com.napier.sem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    private static final String DB_URL =
            "jdbc:mysql://localhost:33060/world" +
                    "?useSSL=false" +
                    "&allowPublicKeyRetrieval=true" +
                    "&serverTimezone=UTC";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "example";

    public static void main(String[] args) {
        try (Connection connection = connectWithRetry()) {
            System.out.println("Database connection successful.");

            CityReports reports = new CityReports(connection);
            reports.runAllReports();

            System.out.println("All city reports completed.");

        } catch (SQLException | IOException e) {
            System.err.println("Application failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Connection connectWithRetry() throws SQLException {
        SQLException lastError = null;

        for (int attempt = 1; attempt <= 10; attempt++) {
            try {
                return DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD);

            } catch (SQLException e) {
                lastError = e;
                System.err.println(
                        "Database connection attempt " + attempt + " failed.");

                if (attempt < 10) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException interrupted) {
                        Thread.currentThread().interrupt();
                        throw new SQLException(
                                "Database connection retry interrupted.",
                                interrupted);
                    }
                }
            }
        }

        throw new SQLException(
                "Could not connect to the World database after 10 attempts.",
                lastError);
    }
}