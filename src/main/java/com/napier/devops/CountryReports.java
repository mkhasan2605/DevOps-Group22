package com.napier.devops;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * UC01 country reports (R01-R06). Every report lists countries from largest
 * to smallest population. Countries without a capital are included, with a null capital.
 */
public class CountryReports {

    private static final String BASE_SELECT =
            "SELECT country.Code, country.Name, country.Continent, country.Region, "
            + "country.Population, city.Name AS Capital "
            + "FROM country LEFT JOIN city ON country.Capital = city.ID ";
    private static final String ORDER_BY = "ORDER BY country.Population DESC ";

    private final Connection con;

    /**
     * Creates the report runner.
     *
     * @param con open connection to the world database
     */
    public CountryReports(Connection con) {
        this.con = con;
    }

    /**
     * R01: all countries in the world.
     *
     * @return all countries, largest population first
     */
    public ArrayList<Country> getAllCountriesByPopulation() {
        return run(BASE_SELECT + ORDER_BY);
    }

    /**
     * R02: all countries in a continent.
     *
     * @param continent continent name, e.g. "Asia"
     * @return countries in the continent, largest population first
     */
    public ArrayList<Country> getCountriesByContinent(String continent) {
        return run(BASE_SELECT + "WHERE country.Continent = ? " + ORDER_BY, continent);
    }

    /**
     * R03: all countries in a region.
     *
     * @param region region name, e.g. "Southeast Asia"
     * @return countries in the region, largest population first
     */
    public ArrayList<Country> getCountriesByRegion(String region) {
        return run(BASE_SELECT + "WHERE country.Region = ? " + ORDER_BY, region);
    }

    /**
     * R04: the top N populated countries in the world.
     *
     * @param n number of countries to return; must be positive
     * @return the top N countries, or an empty list if n is not positive
     */
    public ArrayList<Country> getTopNCountriesWorld(int n) {
        if (!QueryRunner.isValidLimit(n)) {
            return new ArrayList<>();
        }
        return run(BASE_SELECT + ORDER_BY + "LIMIT ?", n);
    }

    /**
     * R05: the top N populated countries in a continent.
     *
     * @param continent continent name, e.g. "Europe"
     * @param n         number of countries to return; must be positive
     * @return the top N countries, or an empty list if n is not positive
     */
    public ArrayList<Country> getTopNCountriesContinent(String continent, int n) {
        if (!QueryRunner.isValidLimit(n)) {
            return new ArrayList<>();
        }
        return run(BASE_SELECT + "WHERE country.Continent = ? " + ORDER_BY + "LIMIT ?",
                continent, n);
    }

    /**
     * R06: the top N populated countries in a region.
     *
     * @param region region name, e.g. "Western Europe"
     * @param n      number of countries to return; must be positive
     * @return the top N countries, or an empty list if n is not positive
     */
    public ArrayList<Country> getTopNCountriesRegion(String region, int n) {
        if (!QueryRunner.isValidLimit(n)) {
            return new ArrayList<>();
        }
        return run(BASE_SELECT + "WHERE country.Region = ? " + ORDER_BY + "LIMIT ?",
                region, n);
    }

    private ArrayList<Country> run(String sql, Object... params) {
        return QueryRunner.query(con, sql, CountryReports::mapCountry, params);
    }

    private static Country mapCountry(ResultSet rs) throws SQLException {
        return new Country(
                rs.getString("Code"),
                rs.getString("Name"),
                rs.getString("Continent"),
                rs.getString("Region"),
                rs.getInt("Population"),
                rs.getString("Capital"));
    }
}
