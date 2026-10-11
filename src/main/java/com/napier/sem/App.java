
package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main application for city population reports R07-R11.
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

        int delayMs = 0;

        if (args.length > 1) {
            try {
                delayMs = Integer.parseInt(args[1]);

                if (delayMs < 0) {
                    System.err.println(
                            "Delay cannot be negative."
                    );
                    return;
                }
            } catch (NumberFormatException e) {
                System.err.println(
                        "Invalid delay. Enter milliseconds."
                );
                return;
            }
        }

        App app = new App();

        try {
            // Load the MySQL JDBC driver.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the World database.
            app.connect(location, delayMs);

            // Create the report service.
            CityReports reports =
                    new CityReports(app.getConnection());

            // Display the report selection menu.
            app.runMenu(reports);

        } catch (ClassNotFoundException e) {
            System.err.println(
                    "MySQL JDBC driver not found. "
                            + "Check your pom.xml dependency."
            );

        } catch (SQLException e) {
            System.err.println(
                    "Database error: " + e.getMessage()
            );

        } finally {
            app.disconnect();
        }
    }

    /**
     * Displays a menu and allows the analyst to choose reports.
     */
    private void runMenu(CityReports reports) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println(
                        "\n========================================"
                );
                System.out.println(
                        "       CITY POPULATION REPORTS"
                );
                System.out.println(
                        "========================================"
                );
                System.out.println(
                        "1. R07 - All cities in the world"
                );
                System.out.println(
                        "2. R08 - Cities in a continent"
                );
                System.out.println(
                        "3. R09 - Cities in a region"
                );
                System.out.println(
                        "4. R10 - Cities in a country"
                );
                System.out.println(
                        "5. R11 - Cities in a district"
                );
                System.out.println("0. Exit");
                System.out.print("Select an option: ");

                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1":
                            reports.printAllCities();
                            break;

                        case "2":
                            String continent = readInput(
                                    scanner, "Enter continent: "
                            );

                            if (continent != null) {
                                reports.printCitiesInContinent(
                                        continent
                                );
                            }
                            break;

                        case "3":
                            String region = readInput(
                                    scanner, "Enter region: "
                            );

                            if (region != null) {
                                reports.printCitiesInRegion(region);
                            }
                            break;

                        case "4":
                            String country = readInput(
                                    scanner, "Enter country: "
                            );

                            if (country != null) {
                                reports.printCitiesInCountry(
                                        country
                                );
                            }
                            break;

                        case "5":
                            String district = readInput(
                                    scanner, "Enter district: "
                            );

                            if (district != null) {
                                reports.printCitiesInDistrict(
                                        district
                                );
                            }
                            break;

                        case "0":
                            running = false;
                            System.out.println(
                                    "Thank you for using City Reports."
                            );
                            break;

                        default:
                            System.out.println(
                                    "Invalid option. Choose 0 to 5."
                            );
                    }

                } catch (SQLException e) {
                    System.err.println(
                            "Could not generate report: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }

    /**
     * Reads a non-empty filter value from the analyst.
     */
    private String readInput(
            Scanner scanner,
            String prompt
    ) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();

        if (value.isEmpty()) {
            System.out.println(
                    "Input cannot be empty. Please try again."
            );
            return null;
        }

        return value;
    }

    /**
     * Connects to MySQL, retrying if the connection fails.
     */
    public void connect(String location, int delayMs)
            throws SQLException {

        String url = "jdbc:mysql://" + location + "/"
                + DATABASE_NAME
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
                "Could not connect after " + RETRIES
                        + " attempts. Check Docker and MySQL.",
                lastException
        );
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection.
     */
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