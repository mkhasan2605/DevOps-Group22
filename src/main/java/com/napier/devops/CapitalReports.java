package com.napier.devops;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Generates capital city population reports.
 * UC03: Requirements R17-R22.
 */
public class CapitalReports {

    private final Connection con;

    /**
     * Uses the existing database connection.
     *
     * @param con Connection to the World database
     */
    public CapitalReports(Connection con) {
        this.con = con;
    }
    /**
     * R17: Gets all capital cities in the world,
     * ordered by population from largest to smallest.
     *
     * @return List of capital cities
     */
    public ArrayList<CapitalCity> getAllCapitalCities() {
        ArrayList<CapitalCity> capitals = new ArrayList<>();

        String sql = "SELECT city.Name AS CapitalName, " +
                "country.Name AS CountryName, " +
                "city.Population " +
                "FROM country " +
                "INNER JOIN city ON country.Capital = city.ID " +
                "ORDER BY city.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CapitalCity capital = new CapitalCity(
                        rs.getString("CapitalName"),
                        rs.getString("CountryName"),
                        rs.getInt("Population")
                );

                capitals.add(capital);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving capital cities: " + e.getMessage());
        }

        return capitals;
    }
    /**
     * R18: Gets all capital cities in a specified continent,
     * ordered by population from largest to smallest.
     *
     * @param continent Name of the continent
     * @return List of capital cities in the continent
     */
    public ArrayList<CapitalCity> getCapitalCitiesByContinent(String continent) {
        ArrayList<CapitalCity> capitals = new ArrayList<>();

        String sql = "SELECT city.Name AS CapitalName, " +
                "country.Name AS CountryName, " +
                "city.Population " +
                "FROM country " +
                "INNER JOIN city ON country.Capital = city.ID " +
                "WHERE country.Continent = ? " +
                "ORDER BY city.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCity capital = new CapitalCity(
                            rs.getString("CapitalName"),
                            rs.getString("CountryName"),
                            rs.getInt("Population")
                    );

                    capitals.add(capital);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving capital cities by continent: "
                    + e.getMessage());
        }

        return capitals;
    }
    /**
     * R19: Gets all capital cities in a specified region,
     * ordered by population from largest to smallest.
     *
     * @param region Name of the region
     * @return List of capital cities in the region
     */
    public ArrayList<CapitalCity> getCapitalCitiesByRegion(String region) {
        ArrayList<CapitalCity> capitals = new ArrayList<>();

        String sql = "SELECT city.Name AS CapitalName, " +
                "country.Name AS CountryName, " +
                "city.Population " +
                "FROM country " +
                "INNER JOIN city ON country.Capital = city.ID " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCity capital = new CapitalCity(
                            rs.getString("CapitalName"),
                            rs.getString("CountryName"),
                            rs.getInt("Population")
                    );

                    capitals.add(capital);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving capital cities by region: "
                    + e.getMessage());
        }

        return capitals;
    }
    /**
     * R20: Gets the top N capital cities in the world,
     * ordered by population from largest to smallest.
     *
     * @param n Number of capital cities to retrieve
     * @return List of the top N capital cities
     */
    public ArrayList<CapitalCity> getTopNCapitalCitiesWorld(int n) {
        ArrayList<CapitalCity> capitals = new ArrayList<>();

        if (n <= 0) {
            System.out.println("N must be greater than zero.");
            return capitals;
        }

        String sql = "SELECT city.Name AS CapitalName, " +
                "country.Name AS CountryName, " +
                "city.Population " +
                "FROM country " +
                "INNER JOIN city ON country.Capital = city.ID " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, n);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCity capital = new CapitalCity(
                            rs.getString("CapitalName"),
                            rs.getString("CountryName"),
                            rs.getInt("Population")
                    );

                    capitals.add(capital);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving top N capital cities: "
                    + e.getMessage());
        }

        return capitals;
    }
    /**
     * R21: Gets the top N capital cities in a specified continent,
     * ordered by population from largest to smallest.
     *
     * @param continent Name of the continent
     * @param n Number of capital cities to retrieve
     * @return List of the top N capital cities in the continent
     */
    public ArrayList<CapitalCity> getTopNCapitalCitiesContinent(String continent, int n) {
        ArrayList<CapitalCity> capitals = new ArrayList<>();

        if (n <= 0) {
            System.out.println("N must be greater than zero.");
            return capitals;
        }

        String sql = "SELECT city.Name AS CapitalName, " +
                "country.Name AS CountryName, " +
                "city.Population " +
                "FROM country " +
                "INNER JOIN city ON country.Capital = city.ID " +
                "WHERE country.Continent = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            stmt.setInt(2, n);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCity capital = new CapitalCity(
                            rs.getString("CapitalName"),
                            rs.getString("CountryName"),
                            rs.getInt("Population")
                    );

                    capitals.add(capital);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving top N capital cities by continent: "
                    + e.getMessage());
        }

        return capitals;
    }
    /**
     * R22: Gets the top N capital cities in a specified region,
     * ordered by population from largest to smallest.
     *
     * @param region Name of the region
     * @param n Number of capital cities to retrieve
     * @return List of the top N capital cities in the region
     */
    public ArrayList<CapitalCity> getTopNCapitalCitiesRegion(String region, int n) {
        ArrayList<CapitalCity> capitals = new ArrayList<>();

        if (n <= 0) {
            System.out.println("N must be greater than zero.");
            return capitals;
        }

        String sql = "SELECT city.Name AS CapitalName, " +
                "country.Name AS CountryName, " +
                "city.Population " +
                "FROM country " +
                "INNER JOIN city ON country.Capital = city.ID " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            stmt.setInt(2, n);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCity capital = new CapitalCity(
                            rs.getString("CapitalName"),
                            rs.getString("CountryName"),
                            rs.getInt("Population")
                    );

                    capitals.add(capital);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving top N capital cities by region: "
                    + e.getMessage());
        }

        return capitals;
    }
}