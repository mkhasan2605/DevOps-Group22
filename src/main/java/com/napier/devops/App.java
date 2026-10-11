package com.napier.devops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Entry point for the population reporting application.
 * Connects to the world database, runs every implemented report and disconnects.
 * Credentials default to root/example and can be overridden with the
 * DB_USER and DB_PASSWORD environment variables.
 */
public class App {

    private static final int RETRIES = 10;
    private static final int MIN_RETRY_DELAY_MS = 1000;
    private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String DB_PASSWORD =
            System.getenv().getOrDefault("DB_PASSWORD", "example");
    private static final int TOP_N = 5;

    private Connection con;

    /**
     * Connects to the database, runs all reports, then disconnects.
     * Exits with status 1 if the database cannot be reached, so CI fails
     * instead of passing silently.
     *
     * <p>Usage:
     * <ul>
     *   <li>Locally (default): no args, uses localhost:33060</li>
     *   <li>Docker/CI: the Dockerfile ENTRYPOINT passes "db:3306 10000"</li>
     *   <li>Custom port: set e.g. "localhost:3307 0" in the IntelliJ Run Configuration</li>
     * </ul>
     *
     * @param args optional database location and start-up delay in milliseconds
     */
    public static void main(String[] args) {
        String location = args.length > 0 ? args[0] : "localhost:33060";
        int delay = args.length > 1 ? parseDelay(args[1]) : 0;

        App app = new App();
        if (!app.connect(location, delay)) {
            System.exit(1);
        }

        boolean ok;
        try {
            ok = app.printCityCount();
            if (ok) {
                app.runReports();
            }
        } finally {
            app.disconnect();
        }
        if (!ok) {
            System.exit(1);
        }
    }

    /**
     * Runs every implemented report and prints the results.
     */
    public void runReports() {
        ReportPrinter printer = new ReportPrinter();
        runCountryReports(printer);
        runCityReports(printer);
        runCapitalReports(printer);
        runPopulationReports(printer);
        runPlacePopulationReports(printer);
    }

    /** UC01 country reports, R01-R06 (Moe Swam Pyae, 40858561). */
    private void runCountryReports(ReportPrinter printer) {
        CountryReports reports = new CountryReports(con);
        printer.printCountries("R01 - All countries in the world",
                reports.getAllCountriesByPopulation());
        printer.printCountries("R02 - All countries in Asia",
                reports.getCountriesByContinent("Asia"));
        printer.printCountries("R03 - All countries in Southeast Asia",
                reports.getCountriesByRegion("Southeast Asia"));
        printer.printCountries("R04 - Top " + TOP_N + " countries in the world",
                reports.getTopNCountriesWorld(TOP_N));
        printer.printCountries("R05 - Top " + TOP_N + " countries in Europe",
                reports.getTopNCountriesContinent("Europe", TOP_N));
        printer.printCountries("R06 - Top " + TOP_N + " countries in Western Europe",
                reports.getTopNCountriesRegion("Western Europe", TOP_N));
    }

    /**
     * UC02 city reports: R07-R11 written to text files (Yashiro, 40841087)
     * and top N cities R12-R16 (Raysharris, 40840483).
     */
    private void runCityReports(ReportPrinter printer) {
        CityReports reports = new CityReports(con);
        reports.runAllReports();
        printer.printCities("R12 - Top " + TOP_N + " cities in the world",
                reports.getTopNCitiesWorld(TOP_N));
        printer.printCities("R13 - Top " + TOP_N + " cities in Asia",
                reports.getTopNCitiesContinent("Asia", TOP_N));
        printer.printCities("R14 - Top " + TOP_N + " cities in Western Europe",
                reports.getTopNCitiesRegion("Western Europe", TOP_N));
        printer.printCities("R15 - Top " + TOP_N + " cities in the United Kingdom",
                reports.getTopNCitiesCountry("United Kingdom", TOP_N));
        printer.printCities("R16 - Top " + TOP_N + " cities in England",
                reports.getTopNCitiesDistrict("England", TOP_N));
    }

    /** UC03 capital city reports, R17-R22 (Ye Yint Aung, 40840498). */
    private void runCapitalReports(ReportPrinter printer) {
        CapitalReports reports = new CapitalReports(con);
        printer.printCapitals("R17 - All capital cities in the world",
                reports.getAllCapitalCities());
        printer.printCapitals("R18 - All capital cities in Asia",
                reports.getCapitalCitiesByContinent("Asia"));
        printer.printCapitals("R19 - All capital cities in Southeast Asia",
                reports.getCapitalCitiesByRegion("Southeast Asia"));
        printer.printCapitals("R20 - Top " + TOP_N + " capital cities in the world",
                reports.getTopNCapitalCitiesWorld(TOP_N));
        printer.printCapitals("R21 - Top " + TOP_N + " capital cities in Asia",
                reports.getTopNCapitalCitiesContinent("Asia", TOP_N));
        printer.printCapitals("R22 - Top " + TOP_N + " capital cities in Southeast Asia",
                reports.getTopNCapitalCitiesRegion("Southeast Asia", TOP_N));
    }

    /** UC04 population in and out of cities, R23-R25 (Mohammad Hasan, 40805651). */
    private void runPopulationReports(ReportPrinter printer) {
        PopulationReports reports = new PopulationReports(con);
        printer.printPopulation("R23 - Population in and out of cities by continent",
                reports.byContinent());
        printer.printPopulation("R24 - Population in and out of cities by region",
                reports.byRegion());
        printer.printPopulation("R25 - Population in and out of cities by country",
                reports.byCountry());
    }

    /** UC05 population of a named place, R27-R29 (Sandiisme, 40841071). */
    private void runPlacePopulationReports(ReportPrinter printer) {
        PlacePopulationReports reports = new PlacePopulationReports(con);
        printer.printPlacePopulation("R27 - Population of Asia",
                reports.getContinentPopulation("Asia"));
        printer.printPlacePopulation("R28 - Population of Southeast Asia",
                reports.getRegionPopulation("Southeast Asia"));
        printer.printPlacePopulation("R29 - Population of the United Kingdom",
                reports.getCountryPopulation("United Kingdom"));
    }

    /**
     * Connects to the world database, retrying while MySQL starts up.
     * Retries wait at least one second, even when no delay is given.
     *
     * @param location database host and port, e.g. "db:3306" (Docker) or "localhost:33060" (local)
     * @param delay    milliseconds to wait before the first attempt and between retries
     * @return true if connected; false if every attempt failed
     */
    public boolean connect(String location, int delay) {
        String url = "jdbc:mysql://" + location
                + "/world?sslMode=DISABLED&allowPublicKeyRetrieval=true";

        for (int i = 1; i <= RETRIES; i++) {
            System.out.println("Connecting to database (attempt " + i + " of " + RETRIES + ")...");
            try {
                Thread.sleep(i == 1 ? delay : Math.max(delay, MIN_RETRY_DELAY_MS));
                con = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
                System.out.println("Successfully connected");
                return true;
            } catch (SQLException e) {
                System.out.println("Failed to connect: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Could not connect to database after " + RETRIES + " attempts");
        return false;
    }

    /**
     * Prints the number of rows in the city table as a check that the database
     * is loaded (the world database has 4079 cities).
     *
     * @return true if the query succeeded
     */
    public boolean printCityCount() {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM city")) {
            if (rs.next()) {
                System.out.println("Cities in database: " + rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
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
            con = null;
        }
    }

    /** Parses the start-up delay argument, falling back to 0 if it is not a number. */
    private static int parseDelay(String value) {
        try {
            return Math.max(0, Integer.parseInt(value));
        } catch (NumberFormatException e) {
            System.out.println("Invalid delay \"" + value + "\", using 0 ms");
            return 0;
        }
    }
}
