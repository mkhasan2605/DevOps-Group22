package com.napier.devops;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * UC05 population information: the population of the world (R26), a continent (R27),
 * region (R28), country (R29) or district (R30).
 * World, continent and region totals are the sum of their countries' populations;
 * a district's total is the sum of its cities, the only district figures in the database.
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
     * R26: the population of the world.
     *
     * @return the world's population, named "World"
     */
    public PlacePopulation getWorldPopulation() {
        ArrayList<PlacePopulation> rows = QueryRunner.query(con,
                "SELECT 'World' AS Name, SUM(Population) AS Population FROM country",
                rs -> new PlacePopulation(rs.getString("Name"), rs.getLong("Population")));
        return rows.get(0);
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

    /**
     * R30: the population of a district, as the total of its cities.
     * The country is needed because district names are not unique.
     *
     * @param district district name, e.g. "England"
     * @param country  name of the country the district is in, e.g. "United Kingdom"
     * @return the district's population, named "District, Country", or null if it is not found
     */
    public PlacePopulation getDistrictPopulation(String district, String country) {
        return run("""
                SELECT CONCAT(city.District, ', ', country.Name) AS Name,
                       SUM(city.Population) AS Population
                FROM city JOIN country ON city.CountryCode = country.Code
                WHERE city.District = ? AND country.Name = ?
                GROUP BY city.District, country.Name
                """, district, country);
    }

    /**
     * Runs a single-row population query for the given place names.
     *
     * @return the place, or null if a name is missing or nothing matches
     */
    private PlacePopulation run(String sql, String... placeNames) {
        Object[] params = new Object[placeNames.length];
        for (int i = 0; i < placeNames.length; i++) {
            if (placeNames[i] == null || placeNames[i].isBlank()) {
                System.out.println("Please enter a valid place name.");
                return null;
            }
            params[i] = placeNames[i].trim();
        }
        ArrayList<PlacePopulation> rows = QueryRunner.query(con, sql,
                rs -> new PlacePopulation(rs.getString("Name"), rs.getLong("Population")),
                params);
        return rows.isEmpty() ? null : rows.get(0);
    }
}
