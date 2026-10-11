package com.napier.devops;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Generates Top N city population reports.
 * UC02: Requirements R12–R16.
 */
public class CityReports {

    /** Database connection shared with App. */
    private final Connection con;

    private static final String BASE_SELECT =
            "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code ";

    /**
     * Uses the existing database connection.
     *
     * @param con Connection to the World database
     */
    public CityReports(Connection con) {
        this.con = con;
    }

    /**
     * R12: The top N populated cities in the world.
     *
     * @param n Number of cities to retrieve
     * @return List of matching City objects
     */
    public ArrayList<City> getTopNCitiesWorld(int n) {
        ArrayList<City> cities = new ArrayList<>();
        if (n <= 0) {
            return cities;
        }

        String sql = BASE_SELECT + "ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, n);
            try (ResultSet rset = stmt.executeQuery()) {
                while (rset.next()) {
                    cities.add(mapCity(rset));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing R12: " + e.getMessage());
        }
        return cities;
    }

    /**
     * R13: The top N populated cities in a continent.
     *
     * @param continent Name of the continent
     * @param n         Number of cities to retrieve
     * @return List of matching City objects
     */
    public ArrayList<City> getTopNCitiesContinent(String continent, int n) {
        ArrayList<City> cities = new ArrayList<>();
        if (n <= 0 || continent == null || continent.isBlank()) {
            return cities;
        }

        String sql = BASE_SELECT + "WHERE country.Continent = ? ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            stmt.setInt(2, n);
            try (ResultSet rset = stmt.executeQuery()) {
                while (rset.next()) {
                    cities.add(mapCity(rset));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing R13: " + e.getMessage());
        }
        return cities;
    }

    /**
     * R14: The top N populated cities in a region.
     *
     * @param region Name of the region
     * @param n      Number of cities to retrieve
     * @return List of matching City objects
     */
    public ArrayList<City> getTopNCitiesRegion(String region, int n) {
        ArrayList<City> cities = new ArrayList<>();
        if (n <= 0 || region == null || region.isBlank()) {
            return cities;
        }

        String sql = BASE_SELECT + "WHERE country.Region = ? ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            stmt.setInt(2, n);
            try (ResultSet rset = stmt.executeQuery()) {
                while (rset.next()) {
                    cities.add(mapCity(rset));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing R14: " + e.getMessage());
        }
        return cities;
    }

    /**
     * R15: The top N populated cities in a country.
     *
     * @param country Name of the country
     * @param n       Number of cities to retrieve
     * @return List of matching City objects
     */
    public ArrayList<City> getTopNCitiesCountry(String country, int n) {
        ArrayList<City> cities = new ArrayList<>();
        if (n <= 0 || country == null || country.isBlank()) {
            return cities;
        }

        String sql = BASE_SELECT + "WHERE country.Name = ? ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, country);
            stmt.setInt(2, n);
            try (ResultSet rset = stmt.executeQuery()) {
                while (rset.next()) {
                    cities.add(mapCity(rset));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing R15: " + e.getMessage());
        }
        return cities;
    }

    /**
     * R16: The top N populated cities in a district.
     *
     * @param district Name of the district
     * @param n        Number of cities to retrieve
     * @return List of matching City objects
     */
    public ArrayList<City> getTopNCitiesDistrict(String district, int n) {
        ArrayList<City> cities = new ArrayList<>();
        if (n <= 0 || district == null || district.isBlank()) {
            return cities;
        }

        String sql = BASE_SELECT + "WHERE city.District = ? ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, district);
            stmt.setInt(2, n);
            try (ResultSet rset = stmt.executeQuery()) {
                while (rset.next()) {
                    cities.add(mapCity(rset));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing R16: " + e.getMessage());
        }
        return cities;
    }

    /**
     * Prints a formatted table of city records.
     *
     * @param title  Header title for the table
     * @param cities List of cities to print
     */
    public void printCities(String title, ArrayList<City> cities) {
        System.out.println();
        System.out.println("====================================================================================================");
        System.out.println(title);
        System.out.println("====================================================================================================");
        System.out.printf("%-35s %-35s %-25s %12s%n", "Name", "Country", "District", "Population");
        System.out.println("----------------------------------------------------------------------------------------------------");

        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities found.");
        } else {
            for (City city : cities) {
                System.out.println(city);
            }
        }
        System.out.println("====================================================================================================");
    }

    private City mapCity(ResultSet rset) throws SQLException {
        return new City(
                rset.getString("Name"),
                rset.getString("Country"),
                rset.getString("District"),
                rset.getInt("Population")
        );
    }
}
