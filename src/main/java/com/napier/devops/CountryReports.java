package com.napier.devops;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Generates country population reports.
 * UC01: Requirements R01-R06.
 */
public class CountryReports {

    /** Database connection shared with App. */
    private final Connection con;

    /**
     * Columns required by the Country Report: Code, Name, Continent, Region, Population, Capital.
     * LEFT JOIN keeps countries that have no capital (shown as N/A).
     */
    private static final String BASE_SELECT =
            "SELECT country.Code, country.Name, country.Continent, country.Region, " +
                    "       country.Population, city.Name AS Capital " +
                    "FROM country " +
                    "LEFT JOIN city ON country.Capital = city.ID ";

    /**
     * Uses the existing database connection.
     *
     * @param con connection to the world database
     */
    public CountryReports(Connection con) {
        this.con = con;
    }

    /**
     * R01: Gets all countries in the world, ordered by population from largest to smallest.
     *
     * @return list of countries
     */
    public ArrayList<Country> getAllCountriesByPopulation() {
        String sql = BASE_SELECT + "ORDER BY country.Population DESC";
        return runQuery(sql);
    }

    /**
     * R02: Gets all countries in a continent, ordered by population from largest to smallest.
     *
     * @param continent name of the continent, e.g. "Asia"
     * @return list of countries in the continent
     */
    public ArrayList<Country> getCountriesByContinent(String continent) {
        String sql = BASE_SELECT +
                "WHERE country.Continent = ? " +
                "ORDER BY country.Population DESC";
        return runQuery(sql, continent);
    }

    /**
     * R03: Gets all countries in a region, ordered by population from largest to smallest.
     *
     * @param region name of the region, e.g. "Southeast Asia"
     * @return list of countries in the region
     */
    public ArrayList<Country> getCountriesByRegion(String region) {
        String sql = BASE_SELECT +
                "WHERE country.Region = ? " +
                "ORDER BY country.Population DESC";
        return runQuery(sql, region);
    }

    /**
     * R04: Gets the top N populated countries in the world.
     *
     * @param n number of countries to return; must be greater than zero
     * @return list of the top N countries, or an empty list if n is not positive
     */
    public ArrayList<Country> getTopNCountriesWorld(int n) {
        if (n <= 0) {
            System.out.println("N must be greater than zero.");
            return new ArrayList<>();
        }
        String sql = BASE_SELECT +
                "ORDER BY country.Population DESC " +
                "LIMIT ?";
        return runQuery(sql, n);
    }

    /**
     * R05: Gets the top N populated countries in a continent.
     *
     * @param continent name of the continent, e.g. "Europe"
     * @param n         number of countries to return; must be greater than zero
     * @return list of the top N countries, or an empty list if n is not positive
     */
    public ArrayList<Country> getTopNCountriesContinent(String continent, int n) {
        if (n <= 0) {
            System.out.println("N must be greater than zero.");
            return new ArrayList<>();
        }
        String sql = BASE_SELECT +
                "WHERE country.Continent = ? " +
                "ORDER BY country.Population DESC " +
                "LIMIT ?";
        return runQuery(sql, continent, n);
    }

    /**
     * R06: Gets the top N populated countries in a region.
     *
     * @param region name of the region, e.g. "Western Europe"
     * @param n      number of countries to return; must be greater than zero
     * @return list of the top N countries, or an empty list if n is not positive
     */
    public ArrayList<Country> getTopNCountriesRegion(String region, int n) {
        if (n <= 0) {
            System.out.println("N must be greater than zero.");
            return new ArrayList<>();
        }
        String sql = BASE_SELECT +
                "WHERE country.Region = ? " +
                "ORDER BY country.Population DESC " +
                "LIMIT ?";
        return runQuery(sql, region, n);
    }

    /**
     * Runs a country query with the given parameters and maps each row to a Country.
     *
     * @param sql    query to run, with ? placeholders
     * @param params values for the placeholders, in order
     * @return the countries found; empty if the query fails
     */
    private ArrayList<Country> runQuery(String sql, Object... params) {
        ArrayList<Country> results = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Country c = new Country();
                    c.setCode(rs.getString("Code"));
                    c.setName(rs.getString("Name"));
                    c.setContinent(rs.getString("Continent"));
                    c.setRegion(rs.getString("Region"));
                    c.setPopulation(rs.getInt("Population"));
                    c.setCapital(rs.getString("Capital"));
                    results.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return results;
    }

    /**
     * Prints countries as an aligned console table.
     *
     * @param countries countries to print; null or empty prints a message instead
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
                    c.getCode(),
                    c.getName(),
                    c.getContinent(),
                    c.getRegion(),
                    c.getPopulation(),
                    c.getCapital() == null ? "N/A" : c.getCapital());
        }

        System.out.println();
        System.out.println("Total: " + countries.size() + " countries");
    }
}