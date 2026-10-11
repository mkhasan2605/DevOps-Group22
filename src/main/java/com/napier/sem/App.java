
package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Main entry point for the city population reports.
 */
public class App {

    private static final String DEFAULT_LOCATION = "localhost:33060";
    private static final String DATABASE_NAME = "world";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "example";
    private static final int RETRIES = 10;

    private Connection connection;

    public static void main(String[] args) {
        String location = args.length > 0
                ? args[0]
                : DEFAULT_LOCATION;

        int delayMs = args.length > 1
                ? Integer.parseInt(args[1])
                : 0;

        App app = new App();

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            app.connect(location, delayMs);

            // Create city reports
            CityReports reports = new CityReports(app.getConnection());

            // R07-R11 reports
            reports.printAllCities();
            reports.printCitiesInContinent("Asia");
            reports.printCitiesInRegion("Southeast Asia");
            reports.printCitiesInCountry("Myanmar");
            reports.printCitiesInDistrict("Yangon");

        } catch (ClassNotFoundException e) {
            System.err.println(
                    "MySQL JDBC driver not found. Check your pom.xml dependency."
            );

        } catch (SQLException e) {
            System.err.println(
                    "Application failed: " + e.getMessage()
            );

        } finally {
            app.disconnect();
        }
    }

    public void connect(String location, int delayMs)
            throws SQLException {

        String url = "jdbc:mysql://" + location + "/" + DATABASE_NAME
                + "?allowPublicKeyRetrieval=true&useSSL=false";

        SQLException lastException = null;

        for (int attempt = 1; attempt <= RETRIES; attempt++) {

            System.out.println(
                    "Connecting to database (attempt "
                            + attempt + " of " + RETRIES + ")..."
            );

            try {
                if (delayMs > 0) {
                    Thread.sleep(delayMs);
                }

                connection = DriverManager.getConnection(
                        url,
                        USERNAME,
                        PASSWORD
                );

                System.out.println(
                        "Successfully connected to MySQL."
                );

                return;

            } catch (SQLException e) {
                lastException = e;

                System.err.println(
                        "Connection failed: " + e.getMessage()
                );

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                throw new SQLException(
                        "Database connection was interrupted.",
                        e
                );
            }
        }

        throw new SQLException(
                "Could not connect after " + RETRIES + " attempts.",
                lastException
        );
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();

                System.out.println(
                        "Database connection closed."
                );

            } catch (SQLException e) {
                System.err.println(
                        "Error closing database connection: "
                                + e.getMessage()
                );
            }
        }
    }
}