package com.napier.devops;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Population report queries: people living in cities vs not, per continent, region or country
 * (requirements R23-R25).
 */
public class PopulationReports {

    /**
     * Total and city population grouped by %s, largest total first.
     * City populations are summed per country first, so countries are never counted twice.
     * LEFT JOIN keeps countries that have no cities in the database.
     */
    private static final String QUERY =
            "SELECT %s AS Name, SUM(co.Population) AS Total, SUM(IFNULL(c.CityPop, 0)) AS InCities "
            + "FROM country co "
            + "LEFT JOIN (SELECT CountryCode, SUM(Population) AS CityPop FROM city GROUP BY CountryCode) c "
            + "ON c.CountryCode = co.Code "
            + "GROUP BY %s "
            + "ORDER BY Total DESC";

    /** Open connection to the world database. */
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
     * R23: population living in cities and not living in cities, for each continent.
     *
     * @return one row per continent, largest population first
     */
    public List<PopulationReport> byContinent() {
        return run(String.format(QUERY, "co.Continent", "co.Continent"));
    }

    /**
     * R24: population living in cities and not living in cities, for each region.
     *
     * @return one row per region, largest population first
     */
    public List<PopulationReport> byRegion() {
        return run(String.format(QUERY, "co.Region", "co.Region"));
    }

    /**
     * R25: population living in cities and not living in cities, for each country.
     *
     * @return one row per country, largest population first
     */
    public List<PopulationReport> byCountry() {
        return run(String.format(QUERY, "co.Name", "co.Code, co.Name"));
    }

    /**
     * Runs a population query and maps each row to a PopulationReport.
     * Column names come only from the constants above, never from user input.
     *
     * @param sql query to run
     * @return the rows found; empty if there is no connection or the query fails
     */
    private List<PopulationReport> run(String sql) {
        List<PopulationReport> result = new ArrayList<>();
        if (con == null) {
            System.out.println("Population report skipped: not connected to the database");
            return result;
        }
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new PopulationReport(
                        rs.getString("Name"),
                        rs.getLong("Total"),
                        rs.getLong("InCities")));
            }
        } catch (SQLException e) {
            System.out.println("Population report failed: " + e.getMessage());
        }
        return result;
    }
}
