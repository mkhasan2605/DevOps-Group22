package com.napier.devops;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Population reports for named places.
 * R27 - Continent population
 * R28 - Region population
 * R29 - Country population
 */
public class PlacePopulationReports {

    /**
     * R27: Returns the population of a continent.
     */
    public PlacePopulation getContinentPopulation(
            Connection con, String continent) throws SQLException {

        String sql = """
                SELECT Continent AS Name,
                       SUM(Population) AS Population
                FROM country
                WHERE Continent = ?
                GROUP BY Continent
                """;

        return getPopulation(con, sql, continent);
    }

    /**
     * R28: Returns the population of a region.
     */
    public PlacePopulation getRegionPopulation(
            Connection con, String region) throws SQLException {

        String sql = """
                SELECT Region AS Name,
                       SUM(Population) AS Population
                FROM country
                WHERE Region = ?
                GROUP BY Region
                """;

        return getPopulation(con, sql, region);
    }

    /**
     * R29: Returns the population of a country.
     */
    public PlacePopulation getCountryPopulation(
            Connection con, String country) throws SQLException {

        String sql = """
                SELECT Name, Population
                FROM country
                WHERE Name = ?
                """;

        return getPopulation(con, sql, country);
    }

    /**
     * Shared method for retrieving a named place's population.
     */
    private PlacePopulation getPopulation(
            Connection con, String sql, String placeName)
            throws SQLException {

        if (con == null) {
            throw new SQLException("Database connection is unavailable.");
        }

        if (placeName == null || placeName.isBlank()) {
            throw new IllegalArgumentException(
                    "Please enter a valid place name.");
        }

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, placeName.trim());

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return new PlacePopulation(
                            rs.getString("Name"),
                            rs.getLong("Population")
                    );
                }
            }
        }

        return null;
    }
}