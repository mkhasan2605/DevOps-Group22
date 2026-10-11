
package com.napier.sem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityReports {

    private final Connection connection;
    private final Path reportDirectory;

    public CityReports(Connection connection) {
        this(connection, Paths.get("reports"));
    }

    // Constructor with a custom output directory, useful for testing.
    public CityReports(Connection connection, Path reportDirectory) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.connection = connection;
        this.reportDirectory = reportDirectory;
    }

    public void runAllReports() throws SQLException, IOException {
        printAllCities();
        printCitiesByContinent();
        printCitiesByRegion();
        printCitiesByCountry();
        printCitiesByDistrict();
    }

    // R07: All cities worldwide, largest population first.
    private void printAllCities() throws SQLException, IOException {
        String sql =
                "SELECT city.Name AS Name, " +
                        "country.Name AS Country, " +
                        "city.District AS District, " +
                        "city.Population AS Population " +
                        "FROM city " +
                        "JOIN country ON city.CountryCode = country.Code " +
                        "ORDER BY city.Population DESC";

        writeReport(
                "R07 - All Cities in the World",
                sql,
                false,
                "R07_All_Cities.txt"
        );
    }

    // R08: Cities grouped by continent, largest population first per continent.
    private void printCitiesByContinent() throws SQLException, IOException {
        String sql =
                "SELECT city.Name AS Name, " +
                        "country.Name AS Country, " +
                        "city.District AS District, " +
                        "city.Population AS Population, " +
                        "country.Continent AS ReportGroup " +
                        "FROM city " +
                        "JOIN country ON city.CountryCode = country.Code " +
                        "ORDER BY country.Continent ASC, city.Population DESC";

        writeReport(
                "R08 - Cities by Continent",
                sql,
                true,
                "R08_Cities_By_Continent.txt"
        );
    }

    // R09: Cities grouped by region, largest population first per region.
    private void printCitiesByRegion() throws SQLException, IOException {
        String sql =
                "SELECT city.Name AS Name, " +
                        "country.Name AS Country, " +
                        "city.District AS District, " +
                        "city.Population AS Population, " +
                        "country.Region AS ReportGroup " +
                        "FROM city " +
                        "JOIN country ON city.CountryCode = country.Code " +
                        "ORDER BY country.Region ASC, city.Population DESC";

        writeReport(
                "R09 - Cities by Region",
                sql,
                true,
                "R09_Cities_By_Region.txt"
        );
    }

    // R10: Cities grouped by country, largest population first per country.
    private void printCitiesByCountry() throws SQLException, IOException {
        String sql =
                "SELECT city.Name AS Name, " +
                        "country.Name AS Country, " +
                        "city.District AS District, " +
                        "city.Population AS Population, " +
                        "country.Name AS ReportGroup " +
                        "FROM city " +
                        "JOIN country ON city.CountryCode = country.Code " +
                        "ORDER BY country.Name ASC, city.Population DESC";

        writeReport(
                "R10 - Cities by Country",
                sql,
                true,
                "R10_Cities_By_Country.txt"
        );
    }

    // R11: Cities grouped by district, largest population first per district.
    private void printCitiesByDistrict() throws SQLException, IOException {
        String sql =
                "SELECT city.Name AS Name, " +
                        "country.Name AS Country, " +
                        "COALESCE(NULLIF(TRIM(city.District), ''), 'Unknown') AS District, " +
                        "city.Population AS Population, " +
                        "COALESCE(NULLIF(TRIM(city.District), ''), 'Unknown') AS ReportGroup " +
                        "FROM city " +
                        "JOIN country ON city.CountryCode = country.Code " +
                        "ORDER BY " +
                        "CASE WHEN COALESCE(NULLIF(TRIM(city.District), ''), 'Unknown') = 'Unknown' " +
                        "THEN 1 ELSE 0 END, " +
                        "ReportGroup ASC, city.Population DESC";

        writeReport(
                "R11 - Cities by District",
                sql,
                true,
                "R11_Cities_By_District.txt"
        );
    }

    private void writeReport(
            String title,
            String sql,
            boolean grouped,
            String fileName
    ) throws SQLException, IOException {

        Files.createDirectories(reportDirectory);
        Path filePath = reportDirectory.resolve(fileName);

        String header = String.format(
                "%-30s | %-30s | %-25s | %15s",
                "Name", "Country", "District", "Population"
        );

        int cityCount = 0;
        String currentGroup = null;

        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath, StandardCharsets.UTF_8);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            writer.write(title);
            writer.newLine();
            writer.write("=".repeat(105));
            writer.newLine();
            writer.write(header);
            writer.newLine();
            writer.write("-".repeat(105));
            writer.newLine();

            while (resultSet.next()) {
                if (grouped) {
                    String group = resultSet.getString("ReportGroup");

                    if (group == null || group.trim().isEmpty()) {
                        group = "Unknown";
                    }

                    if (!group.equals(currentGroup)) {
                        writer.newLine();
                        writer.write("GROUP: " + group);
                        writer.newLine();
                        writer.write("-".repeat(105));
                        writer.newLine();
                        writer.write(header);
                        writer.newLine();
                        writer.write("-".repeat(105));
                        writer.newLine();

                        currentGroup = group;
                    }
                }

                City city = new City(
                        resultSet.getString("Name"),
                        resultSet.getString("Country"),
                        resultSet.getString("District"),
                        resultSet.getLong("Population")
                );

                writer.write(String.format(
                        "%-30s | %-30s | %-25s | %15s",
                        shorten(safe(city.getName()), 30),
                        shorten(safe(city.getCountry()), 30),
                        shorten(safe(city.getDistrict()), 25),
                        String.format("%,d", city.getPopulation())
                ));
                writer.newLine();
                cityCount++;
            }

            writer.newLine();
            writer.write("Total cities in this report: " + cityCount);
            writer.newLine();
        }

        System.out.println(title + " saved to: " + filePath.toAbsolutePath());
        System.out.println("Cities in report: " + cityCount);
    }

    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "N/A" : value;
    }

    private String shorten(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }
}