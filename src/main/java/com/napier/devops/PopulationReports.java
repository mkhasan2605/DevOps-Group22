package com.napier.devops;

import java.sql.Connection;
import java.util.List;

/**
 * UC04 population breakdowns (R23-R25): the population living in cities and
 * not living in cities, for each continent, region or country, largest first.
 * City populations are summed per country before joining, so no country is
 * counted twice, and countries with no cities are still included.
 */
public class PopulationReports {

    private static final String QUERY =
            "SELECT %s AS Name, SUM(co.Population) AS Total, SUM(IFNULL(c.CityPop, 0)) AS InCities "
            + "FROM country co "
            + "LEFT JOIN (SELECT CountryCode, SUM(Population) AS CityPop "
            + "FROM city GROUP BY CountryCode) c "
            + "ON c.CountryCode = co.Code "
            + "GROUP BY %s "
            + "ORDER BY Total DESC";

    private final Connection con;

    /**
     * Creates the report runner.
     *
     * @param con open connection to the world database
     */
    public PopulationReports(Connection con) {
        this.con = con;
    }

    /**
     * R23: population in and out of cities for each continent.
     *
     * @return one row per continent, largest population first
     */
    public List<PopulationReport> byContinent() {
        return run("co.Continent", "co.Continent");
    }

    /**
     * R24: population in and out of cities for each region.
     *
     * @return one row per region, largest population first
     */
    public List<PopulationReport> byRegion() {
        return run("co.Region", "co.Region");
    }

    /**
     * R25: population in and out of cities for each country.
     *
     * @return one row per country, largest population first
     */
    public List<PopulationReport> byCountry() {
        return run("co.Name", "co.Code, co.Name");
    }

    /**
     * Column names are only ever the constants passed by the methods above,
     * never user input, so formatting them into the SQL is safe.
     */
    private List<PopulationReport> run(String nameColumn, String groupBy) {
        return QueryRunner.query(con, String.format(QUERY, nameColumn, groupBy),
                rs -> new PopulationReport(
                        rs.getString("Name"),
                        rs.getLong("Total"),
                        rs.getLong("InCities")));
    }
}
