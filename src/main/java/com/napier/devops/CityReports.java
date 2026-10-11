
package com.napier.devops;

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
import java.util.ArrayList;

/** UC02 city reports: all cities (R07-R11) and top N cities (R12-R16). */
public class CityReports {
    private final Connection con;
    private final Path outputDirectory;

    private static final String BASE_SELECT =
            "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code ";

    public CityReports(Connection con) {
        this(con, Paths.get("reports"));
    }

    public CityReports(Connection con, Path outputDirectory) {
        if (con == null || outputDirectory == null) {
            throw new IllegalArgumentException("Connection and output directory must not be null");
        }
        this.con = con;
        this.outputDirectory = outputDirectory;
    }

    /** R07-R11: create all five text reports. */
    public void runAllReports() throws SQLException, IOException {
        reportAllCities();
        reportCitiesByContinent();
        reportCitiesByRegion();
        reportCitiesByCountry();
        reportCitiesByDistrict();
    }

    /** R07: All cities in the world, ordered by descending population. */
    public void reportAllCities() throws SQLException, IOException {
        writeReport("R07_All_Cities.txt", "R07: All Cities Worldwide",
                BASE_SELECT + "ORDER BY city.Population DESC, city.Name ASC");
    }

    /** R08: All cities grouped by continent. */
    public void reportCitiesByContinent() throws SQLException, IOException {
        writeGroupedReport("R08_Cities_By_Continent.txt", "R08: Cities by Continent",
                "SELECT DISTINCT Continent FROM country ORDER BY Continent",
                BASE_SELECT + "WHERE country.Continent = ? " +
                        "ORDER BY city.Population DESC, city.Name ASC");
    }

    /** R09: All cities grouped by region. */
    public void reportCitiesByRegion() throws SQLException, IOException {
        writeGroupedReport("R09_Cities_By_Region.txt", "R09: Cities by Region",
                "SELECT DISTINCT Region FROM country ORDER BY Region",
                BASE_SELECT + "WHERE country.Region = ? " +
                        "ORDER BY city.Population DESC, city.Name ASC");
    }

    /** R10: All cities grouped by country. */
    public void reportCitiesByCountry() throws SQLException, IOException {
        writeGroupedReport("R10_Cities_By_Country.txt", "R10: Cities by Country",
                "SELECT DISTINCT Name FROM country ORDER BY Name",
                BASE_SELECT + "WHERE country.Name = ? " +
                        "ORDER BY city.Population DESC, city.Name ASC");
    }

    /** R11: All cities grouped by district. */
    public void reportCitiesByDistrict() throws SQLException, IOException {
        writeGroupedReport("R11_Cities_By_District.txt", "R11: Cities by District",
                "SELECT DISTINCT COALESCE(NULLIF(TRIM(District), ''), 'Unknown') " +
                        "AS DistrictName FROM city ORDER BY DistrictName",
                BASE_SELECT + "WHERE COALESCE(NULLIF(TRIM(city.District), ''), 'Unknown') = ? " +
                        "ORDER BY city.Population DESC, city.Name ASC");
    }

    private void writeReport(String fileName, String title, String sql)
            throws SQLException, IOException {
        Files.createDirectories(outputDirectory);
        Path file = outputDirectory.resolve(fileName);
        int count = 0;

        try (BufferedWriter writer =
                     Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writeTitle(writer, title);
            writeColumnHeader(writer);

            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet cities = stmt.executeQuery()) {
                while (cities.next()) {
                    writeCity(writer, cities);
                    count++;
                }
            }

            writer.newLine();
            writer.write("Total cities listed: " + count);
            writer.newLine();
        }

        System.out.println("Created report: " + file.toAbsolutePath());
    }

    private void writeGroupedReport(String fileName, String title,
                                    String groupsSql, String reportSql)
            throws SQLException, IOException {
        Files.createDirectories(outputDirectory);
        Path file = outputDirectory.resolve(fileName);
        int totalCities = 0;

        try (BufferedWriter writer =
                     Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writeTitle(writer, title);

            try (PreparedStatement groupStmt = con.prepareStatement(groupsSql);
                 ResultSet groups = groupStmt.executeQuery()) {

                while (groups.next()) {
                    String groupName = groups.getString(1);

                    writer.newLine();
                    writer.write("GROUP: " + groupName);
                    writer.newLine();
                    writeColumnHeader(writer);

                    int groupCount = 0;

                    try (PreparedStatement cityStmt =
                                 con.prepareStatement(reportSql)) {
                        cityStmt.setString(1, groupName);

                        try (ResultSet cities = cityStmt.executeQuery()) {
                            while (cities.next()) {
                                writeCity(writer, cities);
                                groupCount++;
                                totalCities++;
                            }
                        }
                    }

                    writer.write("Cities in this group: " + groupCount);
                    writer.newLine();
                }
            }

            writer.newLine();
            writer.write("Total cities listed: " + totalCities);
            writer.newLine();
        }

        System.out.println("Created report: " + file.toAbsolutePath());
    }

    private void writeTitle(BufferedWriter writer, String title)
            throws IOException {
        writer.write(title);
        writer.newLine();
        writer.write("=".repeat(title.length()));
        writer.newLine();
    }

    private void writeColumnHeader(BufferedWriter writer)
            throws IOException {
        writer.write(String.format(
                "%-30s | %-35s | %-25s | %12s",
                "City", "Country", "District", "Population"));
        writer.newLine();
        writer.write("-".repeat(115));
        writer.newLine();
    }

    private void writeCity(BufferedWriter writer, ResultSet rs)
            throws SQLException, IOException {
        writer.write(String.format(
                "%-30s | %-35s | %-25s | %12d",
                shorten(rs.getString("Name"), 30),
                shorten(rs.getString("Country"), 35),
                shorten(rs.getString("District"), 25),
                rs.getLong("Population")));
        writer.newLine();
    }

    private String shorten(String value, int maxLength) {
        if (value == null || value.trim().isEmpty()) {
            return "Unknown";
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    /** R12: The top N populated cities in the world. */
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

    /** R13: The top N populated cities in a continent. */
    public ArrayList<City> getTopNCitiesContinent(String continent, int n) {
        ArrayList<City> cities = new ArrayList<>();
        if (n <= 0 || continent == null || continent.isBlank()) {
            return cities;
        }

        String sql = BASE_SELECT +
                "WHERE country.Continent = ? ORDER BY city.Population DESC LIMIT ?";

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

    /** R14: The top N populated cities in a region. */
    public ArrayList<City> getTopNCitiesRegion(String region, int n) {
        ArrayList<City> cities = new ArrayList<>();
        if (n <= 0 || region == null || region.isBlank()) {
            return cities;
        }

        String sql = BASE_SELECT +
                "WHERE country.Region = ? ORDER BY city.Population DESC LIMIT ?";

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

    /** R15: The top N populated cities in a country. */
    public ArrayList<City> getTopNCitiesCountry(String country, int n) {
        ArrayList<City> cities = new ArrayList<>();
        if (n <= 0 || country == null || country.isBlank()) {
            return cities;
        }

        String sql = BASE_SELECT +
                "WHERE country.Name = ? ORDER BY city.Population DESC LIMIT ?";

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

    /** R16: The top N populated cities in a district. */
    public ArrayList<City> getTopNCitiesDistrict(String district, int n) {
        ArrayList<City> cities = new ArrayList<>();
        if (n <= 0 || district == null || district.isBlank()) {
            return cities;
        }

        String sql = BASE_SELECT +
                "WHERE city.District = ? ORDER BY city.Population DESC LIMIT ?";

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

    /** Print a formatted table of city records to the console. */
    public void printCities(String title, ArrayList<City> cities) {
        System.out.println();
        System.out.println("====================================================================================================");
        System.out.println(title);
        System.out.println("====================================================================================================");
        System.out.printf(
                "%-35s %-35s %-25s %12s%n",
                "Name", "Country", "District", "Population");
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
