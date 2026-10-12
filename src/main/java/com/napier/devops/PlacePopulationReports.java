package com.napier.devops;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * UC05 population of a named place: a continent (R27), region (R28) or country (R29).
 * Continent and region totals are the sum of their countries' populations.
 */
public class PlacePopulationReports {

    private final Connection con;

    /**
     * Creates the report runner.
     *
     * @param con open connection to the world database
     */
    public PlacePopulationReports(Connection con) {
        this.con = con;
    }

    /**
     * R27: the population of a continent.
     *
     * @param continent continent name, e.g. "Asia"
     * @return the continent's population, or null if it is not found
     */
    public PlacePopulation getContinentPopulation(String continent) {
        return run("""
                SELECT Continent AS Name, SUM(Population) AS Population
                FROM country
                WHERE Continent = ?
                GROUP BY Continent
                """, continent);
    }

    /**
     * R28: the population of a region.
     *
     * @param region region name, e.g. "Southeast Asia"
     * @return the region's population, or null if it is not found
     */
    public PlacePopulation getRegionPopulation(String region) {
        return run("""
                SELECT Region AS Name, SUM(Population) AS Population
                FROM country
                WHERE Region = ?
                GROUP BY Region
                """, region);
    }

    /**
     * R29: the population of a country.
     *
     * @param country country name, e.g. "United Kingdom"
     * @return the country's population, or null if it is not found
     */
    public PlacePopulation getCountryPopulation(String country) {
        return run("""
                SELECT Name, Population
                FROM country
                WHERE Name = ?
                """, country);
    }

    private PlacePopulation run(String sql, String placeName) {
        if (placeName == null || placeName.isBlank()) {
            System.out.println("Please enter a valid place name.");
            return null;
        }
        ArrayList<PlacePopulation> rows = QueryRunner.query(con, sql,
                rs -> new PlacePopulation(rs.getString("Name"), rs.getLong("Population")),
                placeName.trim());
        return rows.isEmpty() ? null : rows.get(0);
    }
}
