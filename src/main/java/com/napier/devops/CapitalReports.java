package com.napier.devops;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * UC03 capital city reports (R17-R22). Every report lists capital cities from
 * largest to smallest population. Countries without a capital are left out.
 */
public class CapitalReports {

    private static final String BASE_SELECT =
            "SELECT city.Name AS CapitalName, country.Name AS CountryName, city.Population "
            + "FROM country JOIN city ON country.Capital = city.ID ";
    private static final String ORDER_BY = "ORDER BY city.Population DESC ";

    private final Connection con;

    /**
     * Creates the report runner.
     *
     * @param con open connection to the world database
     */
    public CapitalReports(Connection con) {
        this.con = con;
    }

    /**
     * R17: all capital cities in the world.
     *
     * @return all capital cities, largest population first
     */
    public ArrayList<CapitalCity> getAllCapitalCities() {
        return run(BASE_SELECT + ORDER_BY);
    }

    /**
     * R18: all capital cities in a continent.
     *
     * @param continent continent name, e.g. "Asia"
     * @return capital cities in the continent, largest population first
     */
    public ArrayList<CapitalCity> getCapitalCitiesByContinent(String continent) {
        return run(BASE_SELECT + "WHERE country.Continent = ? " + ORDER_BY, continent);
    }

    /**
     * R19: all capital cities in a region.
     *
     * @param region region name, e.g. "Southeast Asia"
     * @return capital cities in the region, largest population first
     */
    public ArrayList<CapitalCity> getCapitalCitiesByRegion(String region) {
        return run(BASE_SELECT + "WHERE country.Region = ? " + ORDER_BY, region);
    }

    /**
     * R20: the top N populated capital cities in the world.
     *
     * @param n number of capital cities to return; must be positive
     * @return the top N capital cities, or an empty list if n is not positive
     */
    public ArrayList<CapitalCity> getTopNCapitalCitiesWorld(int n) {
        if (!QueryRunner.isValidLimit(n)) {
            return new ArrayList<>();
        }
        return run(BASE_SELECT + ORDER_BY + "LIMIT ?", n);
    }

    /**
     * R21: the top N populated capital cities in a continent.
     *
     * @param continent continent name, e.g. "Asia"
     * @param n         number of capital cities to return; must be positive
     * @return the top N capital cities, or an empty list if n is not positive
     */
    public ArrayList<CapitalCity> getTopNCapitalCitiesContinent(String continent, int n) {
        if (!QueryRunner.isValidLimit(n)) {
            return new ArrayList<>();
        }
        return run(BASE_SELECT + "WHERE country.Continent = ? " + ORDER_BY + "LIMIT ?",
                continent, n);
    }

    /**
     * R22: the top N populated capital cities in a region.
     *
     * @param region region name, e.g. "Southeast Asia"
     * @param n      number of capital cities to return; must be positive
     * @return the top N capital cities, or an empty list if n is not positive
     */
    public ArrayList<CapitalCity> getTopNCapitalCitiesRegion(String region, int n) {
        if (!QueryRunner.isValidLimit(n)) {
            return new ArrayList<>();
        }
        return run(BASE_SELECT + "WHERE country.Region = ? " + ORDER_BY + "LIMIT ?",
                region, n);
    }

    private ArrayList<CapitalCity> run(String sql, Object... params) {
        return QueryRunner.query(con, sql, CapitalReports::mapCapital, params);
    }

    private static CapitalCity mapCapital(ResultSet rs) throws SQLException {
        return new CapitalCity(
                rs.getString("CapitalName"),
                rs.getString("CountryName"),
                rs.getInt("Population"));
    }
}
