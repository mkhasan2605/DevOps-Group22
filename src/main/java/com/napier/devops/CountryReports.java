package com.napier.devops;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Provides the country-based reports for UC01 (R01-R06).
 * Each method returns a list of Country objects that can be printed by printCountries().
 */
public class CountryReports {

    /** Database connection shared with App. */
    private final Connection con;

    /**
     * Base SELECT that joins country with city to resolve the capital city name.
     * LEFT JOIN is used so countries with no capital still appear (Capital = null).
     */
    private static final String BASE_SELECT =
            "SELECT country.Code, country.Name, country.Continent, country.Region, " +
                    "       country.Population, city.Name AS Capital " +
                    "FROM country " +
                    "LEFT JOIN city ON country.Capital = city.ID ";

    /**
     * Creates a new CountryReports using an open database connection.
     *
     * @param con an open JDBC connection to the world database
     */
    public CountryReports(Connection con) {
        this.con = con;
    }

    // ------------------------------------------------------------------
    // R01 - All countries in the world, largest population first
    // ------------------------------------------------------------------
    /**
     * R01: Every country in the world, ordered by population descending.
     *
     * @return a list of Country objects, or an empty list on error
     */
    public ArrayList<Country> getAllCountriesByPopulation() {
        String sql = BASE_SELECT + "ORDER BY country.Population DESC";
        return runQuery(sql);
    }

    // ------------------------------------------------------------------
    // R02 - All countries in a continent, largest population first
    // ------------------------------------------------------------------
    /**
     * R02: Every country in a given continent, ordered by population descending.
     *
     * @param continent the continent name, e.g. "Asia"
     * @return a list of Country objects, or an empty list on error
     */
    public ArrayList<Country> getCountriesByContinent(String continent) {
        String sql = BASE_SELECT +
                "WHERE country.Continent = '" + continent + "' " +
                "ORDER BY country.Population DESC";
        return runQuery(sql);
    }

    // ------------------------------------------------------------------
    // R03 - All countries in a region, largest population first
    // ------------------------------------------------------------------
    /**
     * R03: Every country in a given region, ordered by population descending.
     *
     * @param region the region name, e.g. "Southeast Asia"
     * @return a list of Country objects, or an empty list on error
     */
    public ArrayList<Country> getCountriesByRegion(String region) {
        String sql = BASE_SELECT +
                "WHERE country.Region = '" + region + "' " +
                "ORDER BY country.Population DESC";
        return runQuery(sql);
    }

    // ------------------------------------------------------------------
    // R04 - Top N countries in the world
    // ------------------------------------------------------------------
    /**
     * R04: The top N countries in the world by population.
     *
     * @param n how many countries to return
     * @return a list of Country objects, or an empty list on error
     */
    public ArrayList<Country> getTopNCountriesWorld(int n) {
        String sql = BASE_SELECT +
                "ORDER BY country.Population DESC " +
                "LIMIT " + n;
        return runQuery(sql);
    }

    // ------------------------------------------------------------------
    // R05 - Top N countries in a continent
    // ------------------------------------------------------------------
    /**
     * R05: The top N countries in a continent by population.
     *
     * @param continent the continent name, e.g. "Europe"
     * @param n         how many countries to return
     * @return a list of Country objects, or an empty list on error
     */
    public ArrayList<Country> getTopNCountriesContinent(String continent, int n) {
        String sql = BASE_SELECT +
                "WHERE country.Continent = '" + continent + "' " +
                "ORDER BY country.Population DESC " +
                "LIMIT " + n;
        return runQuery(sql);
    }

    // ------------------------------------------------------------------
    // R06 - Top N countries in a region
    // ------------------------------------------------------------------
    /**
     * R06: The top N countries in a region by population.
     *
     * @param region the region name, e.g. "Western Europe"
     * @param n      how many countries to return
     * @return a list of Country objects, or an empty list on error
     */
    public ArrayList<Country> getTopNCountriesRegion(String region, int n) {
        String sql = BASE_SELECT +
                "WHERE country.Region = '" + region + "' " +
                "ORDER BY country.Population DESC " +
                "LIMIT " + n;
        return runQuery(sql);
    }

    // ------------------------------------------------------------------
    // Helper - runs any of the above queries and maps the ResultSet
    // ------------------------------------------------------------------
    /**
     * Runs a SELECT query against the country table and maps each row
     * to a Country object.
     *
     * @param sql a SELECT statement returning Code, Name, Continent,
     *            Region, Population, Capital in that order
     * @return a list of Country objects, or an empty list on error
     */
    private ArrayList<Country> runQuery(String sql) {
        ArrayList<Country> results = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country c = new Country();
                c.code       = rs.getString("Code");
                c.name       = rs.getString("Name");
                c.continent  = rs.getString("Continent");
                c.region     = rs.getString("Region");
                c.population = rs.getInt("Population");
                c.capital    = rs.getString("Capital");
                results.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return results;
    }

    // ------------------------------------------------------------------
    // Print helper
    // ------------------------------------------------------------------
    /**
     * Prints a list of countries in the standard Country Report format.
     * Columns: Code, Name, Continent, Region, Population, Capital.
     *
     * @param countries the list of countries to print
     */
    public void printCountries(ArrayList<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries to display.");
            return;
        }

        System.out.printf("%-5s %-40s %-15s %-25s %-12s %-30s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println("-".repeat(130));

        for (Country c : countries) {
            System.out.printf("%-5s %-40s %-15s %-25s %-12d %-30s%n",
                    c.code,
                    c.name,
                    c.continent,
                    c.region,
                    c.population,
                    c.capital == null ? "N/A" : c.capital);
        }

        System.out.println();
        System.out.println("Total: " + countries.size() + " countries");
    }
}