package com.napier.devops;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CountryReports {

    /** Database connection shared with App. */
    private final Connection con;


    private static final String BASE_SELECT =
            "SELECT country.Code, country.Name, country.Continent, country.Region, " +
                    "       country.Population, city.Name AS Capital " +
                    "FROM country " +
                    "LEFT JOIN city ON country.Capital = city.ID ";


    public CountryReports(Connection con) {
        this.con = con;
    }


    public ArrayList<Country> getAllCountriesByPopulation() {
        String sql = BASE_SELECT + "ORDER BY country.Population DESC";
        return runQuery(sql);
    }



    public ArrayList<Country> getCountriesByContinent(String continent) {
        String sql = BASE_SELECT +
                "WHERE country.Continent = ? " +
                "ORDER BY country.Population DESC";
        return runQuery(sql, continent);
    }


    public ArrayList<Country> getCountriesByRegion(String region) {
        String sql = BASE_SELECT +
                "WHERE country.Region = ? " +
                "ORDER BY country.Population DESC";
        return runQuery(sql, region);
    }


    public ArrayList<Country> getTopNCountriesWorld(int n) {
        String sql = BASE_SELECT +
                "ORDER BY country.Population DESC " +
                "LIMIT ?";
        return runQuery(sql, n);
    }

    public ArrayList<Country> getTopNCountriesContinent(String continent, int n) {
        String sql = BASE_SELECT +
                "WHERE country.Continent = ? " +
                "ORDER BY country.Population DESC " +
                "LIMIT ?";
        return runQuery(sql, continent, n);
    }



    public ArrayList<Country> getTopNCountriesRegion(String region, int n) {
        String sql = BASE_SELECT +
                "WHERE country.Region = ? " +
                "ORDER BY country.Population DESC " +
                "LIMIT ?";
        return runQuery(sql, region, n);
    }



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