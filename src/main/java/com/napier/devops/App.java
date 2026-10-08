package com.napier.devops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Entry point for the population reporting application.
 */
public class App {

    /** Number of connection attempts before giving up. */
    private static final int RETRIES = 10;

    /** Open connection to the world database, or null if not connected. */
    Connection con;

    /**
     * Connects to the database, runs the UC01 country reports, then disconnects.
     *
     * When run inside Docker (e.g. on GitHub Actions), pass "db:3306" as the
     * first argument. When run locally, pass "localhost:3307".
     *
     * @param args optional database location and delay
     */
    public static void main(String[] args) {
        String location = args.length > 0 ? args[0] : "db:3306";
        int delay = args.length > 1 ? Integer.parseInt(args[1]) : 0;

        App app = new App();
        app.connect(location, delay);

        // UC01 - Country Reports (MoeSwamPyae, 40858561)
        CountryReports reports = new CountryReports(app.con);

        System.out.println("========== R01: All countries in the world ==========");
        reports.printCountries(reports.getAllCountriesByPopulation());

        System.out.println("========== R02: All countries in Asia ==========");
        reports.printCountries(reports.getCountriesByContinent("Asia"));

        System.out.println("========== R03: All countries in Southeast Asia ==========");
        reports.printCountries(reports.getCountriesByRegion("Southeast Asia"));

        System.out.println("========== R04: Top 5 countries in the world ==========");
        reports.printCountries(reports.getTopNCountriesWorld(5));

        System.out.println("========== R05: Top 5 countries in Europe ==========");
        reports.printCountries(reports.getTopNCountriesContinent("Europe", 5));

        System.out.println("========== R06: Top 5 countries in Western Europe ==========");
        reports.printCountries(reports.getTopNCountriesRegion("Western Europe", 5));

        app.disconnect();
    }

    /**
     * Connects to the world database, retrying while MySQL starts up.
     * Exits with status 1 if every attempt fails, so CI goes red instead of falsely green.
     *
     * @param location database host and port, e.g. "db:3306" (Docker) or "localhost:3307" (local)
     * @param delay    milliseconds to wait before each attempt
     */
    public void connect(String location, int delay) {
        String url = "jdbc:mysql://" + location
                + "/world?sslMode=DISABLED&allowPublicKeyRetrieval=true";

        for (int i = 1; i <= RETRIES; i++) {
            System.out.println("Connecting to database (attempt " + i + " of " + RETRIES + ")...");
            try {
                Thread.sleep(delay);
                con = DriverManager.getConnection(url, "root", "example");
                System.out.println("Successfully connected");
                return;
            } catch (SQLException e) {
                System.out.println("Failed to connect: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Could not connect to database after " + RETRIES + " attempts");
        System.exit(1);
    }

    /**
     * Prints the number of rows in the city table. The world database has 4079.
     */
    public void printCityCount() {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM city")) {
            if (rs.next()) {
                System.out.println("Cities in database: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Closes the database connection if one is open.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}