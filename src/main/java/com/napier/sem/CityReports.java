
package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Generates city population reports R07-R11.
 */
public class CityReports {

    private final Connection connection;

    public CityReports(Connection connection) {
        this.connection = connection;
    }

    /**
     * R07: All cities in the world,
     * ordered from largest population to smallest.
     */
    public void printAllCities() throws SQLException {
        String sql =
                "SELECT city.Name AS City, country.Name AS Country, "
                        + "city.District, city.Population "
                        + "FROM city "
                        + "JOIN country "
                        + "ON city.CountryCode = country.Code "
                        + "ORDER BY city.Population DESC";

        printReport(
                "R07 - All Cities in the World",
                sql,
                null
        );
    }

    /**
     * R08: All cities in a selected continent.
     */
    public void printCitiesInContinent(String continent)
            throws SQLException {

        String sql =
                "SELECT city.Name AS City, country.Name AS Country, "
                        + "city.District, city.Population "
                        + "FROM city "
                        + "JOIN country "
                        + "ON city.CountryCode = country.Code "
                        + "WHERE country.Continent = ? "
                        + "ORDER BY city.Population DESC";

        printReport(
                "R08 - Cities in Continent: " + continent,
                sql,
                continent
        );
    }

    /**
     * R09: All cities in a selected region.
     */
    public void printCitiesInRegion(String region)
            throws SQLException {

        String sql =
                "SELECT city.Name AS City, country.Name AS Country, "
                        + "city.District, city.Population "
                        + "FROM city "
                        + "JOIN country "
                        + "ON city.CountryCode = country.Code "
                        + "WHERE country.Region = ? "
                        + "ORDER BY city.Population DESC";

        printReport(
                "R09 - Cities in Region: " + region,
                sql,
                region
        );
    }

    /**
     * R10: All cities in a selected country.
     */
    public void printCitiesInCountry(String countryName)
            throws SQLException {

        String sql =
                "SELECT city.Name AS City, country.Name AS Country, "
                        + "city.District, city.Population "
                        + "FROM city "
                        + "JOIN country "
                        + "ON city.CountryCode = country.Code "
                        + "WHERE country.Name = ? "
                        + "ORDER BY city.Population DESC";

        printReport(
                "R10 - Cities in Country: " + countryName,
                sql,
                countryName
        );
    }

    /**
     * R11: All cities in a selected district.
     */
    public void printCitiesInDistrict(String district)
            throws SQLException {

        String sql =
                "SELECT city.Name AS City, country.Name AS Country, "
                        + "city.District, city.Population "
                        + "FROM city "
                        + "JOIN country "
                        + "ON city.CountryCode = country.Code "
                        + "WHERE city.District = ? "
                        + "ORDER BY city.Population DESC";

        printReport(
                "R11 - Cities in District: " + district,
                sql,
                district
        );
    }

    /**
     * Executes a report and prints the result.
     * A null parameter means that no filter is required.
     */
    private void printReport(
            String title,
            String sql,
            String parameter
    ) throws SQLException {

        System.out.println(
                "\n=================================================="
        );
        System.out.println(title);
        System.out.println(
                "=================================================="
        );

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            if (parameter != null) {
                statement.setString(1, parameter);
            }

            try (ResultSet results = statement.executeQuery()) {
                System.out.printf(
                        "%-30s %-35s %-25s %15s%n",
                        "City",
                        "Country",
                        "District",
                        "Population"
                );

                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------"
                );

                int count = 0;

                while (results.next()) {
                    System.out.printf(
                            "%-30s %-35s %-25s %,15d%n",
                            results.getString("City"),
                            results.getString("Country"),
                            results.getString("District"),
                            results.getLong("Population")
                    );

                    count++;
                }

                if (count == 0) {
                    System.out.println(
                            "No cities found for this report."
                    );
                } else {
                    System.out.println(
                            "\nTotal cities found: " + count
                    );
                }
            }
        }
    }
}