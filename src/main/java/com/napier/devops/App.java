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
    private Connection con;

    /**
     * Connects to the database, runs a sanity check, then disconnects.
     *
     * @param args optional database location and delay, see class comment
     */
    public static void main(String[] args) {
        String location = args.length > 0 ? args[0] : "localhost:33060";
        int delay = args.length > 1 ? Integer.parseInt(args[1]) : 0;

        App app = new App();
        app.connect(location, delay);
        app.printCityCount();
        app.disconnect();
    }

    /**
     * Connects to the world database, retrying while MySQL starts up.
     * Exits with status 1 if every attempt fails, so CI goes red instead of falsely green.
     *
     * @param location database host and port, e.g. db:3306
     * @param delay    milliseconds to wait before each attempt
     */
    public void connect(String location, int delay) {
        String url = "jdbc:mysql://" + location
                + "/world?allowPublicKeyRetrieval=true&useSSL=false";

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
