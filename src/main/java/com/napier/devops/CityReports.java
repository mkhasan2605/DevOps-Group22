package com.napier.devops;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * UC02 city reports. R07-R11 write every city to a text file, grouped by
 * continent, region, country or district; R12-R16 return the top N cities.
 * Cities are always listed from largest to smallest population, and blank
 * districts are reported as "Unknown".
 */
public class CityReports {

    private static final String COLUMNS =
            "city.Name, country.Name AS Country, city.District, city.Population ";
    private static final String FROM =
            "FROM city JOIN country ON city.CountryCode = country.Code ";
    private static final String ORDER_BY = "ORDER BY city.Population DESC, city.Name ASC ";
    private static final String DISTRICT_NAME =
            "COALESCE(NULLIF(TRIM(city.District), ''), 'Unknown')";
    private static final String FILE_ROW_FORMAT = "%-30s | %-35s | %-25s | %12s";

    private final Connection con;
    private final Path outputDirectory;

    /** A city together with the group (continent, region, ...) it is reported under. */
    private record GroupedCity(String group, City city) {
    }

    /**
     * Creates the report runner, writing file reports to the "reports" directory.
     *
     * @param con open connection to the world database
     */
    public CityReports(Connection con) {
        this(con, Paths.get("reports"));
    }

    /**
     * Creates the report runner.
     *
     * @param con             open connection to the world database
     * @param outputDirectory directory the R07-R11 text reports are written to
     */
    public CityReports(Connection con, Path outputDirectory) {
        this.con = con;
        this.outputDirectory = Objects.requireNonNull(outputDirectory, "outputDirectory");
    }

    /** Writes all five R07-R11 text reports. */
    public void runAllReports() {
        reportAllCities();
        reportCitiesByContinent();
        reportCitiesByRegion();
        reportCitiesByCountry();
        reportCitiesByDistrict();
    }

    /** R07: writes all cities in the world to R07_All_Cities.txt. */
    public void reportAllCities() {
        ArrayList<City> cities = QueryRunner.query(con,
                "SELECT " + COLUMNS + FROM + ORDER_BY, CityReports::mapCity);

        writeFile("R07_All_Cities.txt", "R07: All Cities Worldwide", writer -> {
            writeColumnHeader(writer);
            for (City city : cities) {
                writeCity(writer, city);
            }
            writer.newLine();
            writer.write("Total cities listed: " + cities.size());
            writer.newLine();
        });
    }

    /** R08: writes all cities, grouped by continent, to R08_Cities_By_Continent.txt. */
    public void reportCitiesByContinent() {
        writeGroupedReport("R08_Cities_By_Continent.txt",
                "R08: Cities by Continent", "country.Continent");
    }

    /** R09: writes all cities, grouped by region, to R09_Cities_By_Region.txt. */
    public void reportCitiesByRegion() {
        writeGroupedReport("R09_Cities_By_Region.txt",
                "R09: Cities by Region", "country.Region");
    }

    /** R10: writes all cities, grouped by country, to R10_Cities_By_Country.txt. */
    public void reportCitiesByCountry() {
        writeGroupedReport("R10_Cities_By_Country.txt",
                "R10: Cities by Country", "country.Name");
    }

    /** R11: writes all cities, grouped by district, to R11_Cities_By_District.txt. */
    public void reportCitiesByDistrict() {
        writeGroupedReport("R11_Cities_By_District.txt",
                "R11: Cities by District", DISTRICT_NAME);
    }

    /**
     * R12: the top N populated cities in the world.
     *
     * @param n number of cities to return; must be positive
     * @return the top N cities, or an empty list if n is not positive
     */
    public ArrayList<City> getTopNCitiesWorld(int n) {
        return topN("", n);
    }

    /**
     * R13: the top N populated cities in a continent.
     *
     * @param continent continent name, e.g. "Asia"
     * @param n         number of cities to return; must be positive
     * @return the top N cities, or an empty list if n is not positive
     */
    public ArrayList<City> getTopNCitiesContinent(String continent, int n) {
        return topN("WHERE country.Continent = ? ", n, continent);
    }

    /**
     * R14: the top N populated cities in a region.
     *
     * @param region region name, e.g. "Western Europe"
     * @param n      number of cities to return; must be positive
     * @return the top N cities, or an empty list if n is not positive
     */
    public ArrayList<City> getTopNCitiesRegion(String region, int n) {
        return topN("WHERE country.Region = ? ", n, region);
    }

    /**
     * R15: the top N populated cities in a country.
     *
     * @param country country name, e.g. "United Kingdom"
     * @param n       number of cities to return; must be positive
     * @return the top N cities, or an empty list if n is not positive
     */
    public ArrayList<City> getTopNCitiesCountry(String country, int n) {
        return topN("WHERE country.Name = ? ", n, country);
    }

    /**
     * R16: the top N populated cities in a district.
     *
     * @param district district name, e.g. "England"; "Unknown" matches blank districts
     * @param n        number of cities to return; must be positive
     * @return the top N cities, or an empty list if n is not positive
     */
    public ArrayList<City> getTopNCitiesDistrict(String district, int n) {
        return topN("WHERE " + DISTRICT_NAME + " = ? ", n, district);
    }

    /**
     * Runs a top N query.
     *
     * @param where  WHERE clause (with a trailing space), or "" for the whole world
     * @param n      LIMIT value
     * @param filter value for the WHERE placeholder, if there is one
     */
    private ArrayList<City> topN(String where, int n, Object... filter) {
        if (!QueryRunner.isValidLimit(n)) {
            return new ArrayList<>();
        }
        Object[] params = new Object[filter.length + 1];
        System.arraycopy(filter, 0, params, 0, filter.length);
        params[filter.length] = n;
        return QueryRunner.query(con, "SELECT " + COLUMNS + FROM + where + ORDER_BY + "LIMIT ?",
                CityReports::mapCity, params);
    }

    /**
     * Writes every city in one query ordered by group, starting a new section
     * in the file each time the group changes. The binary sort key keeps names
     * that the case/accent-insensitive collation treats as equal in separate groups.
     *
     * @param groupColumn SQL expression the cities are grouped by
     */
    private void writeGroupedReport(String fileName, String title, String groupColumn) {
        ArrayList<GroupedCity> rows = QueryRunner.query(con,
                "SELECT " + groupColumn + " AS GroupName, " + COLUMNS + FROM
                        + "ORDER BY " + groupColumn + ", CAST(" + groupColumn + " AS BINARY), "
                        + "city.Population DESC, city.Name ASC",
                rs -> new GroupedCity(rs.getString("GroupName"), mapCity(rs)));

        writeFile(fileName, title, writer -> {
            String currentGroup = null;
            int groupCount = 0;
            for (GroupedCity row : rows) {
                if (!row.group().equals(currentGroup)) {
                    if (currentGroup != null) {
                        writeGroupFooter(writer, groupCount);
                    }
                    currentGroup = row.group();
                    groupCount = 0;
                    writer.newLine();
                    writer.write("GROUP: " + currentGroup);
                    writer.newLine();
                    writeColumnHeader(writer);
                }
                writeCity(writer, row.city());
                groupCount++;
            }
            if (currentGroup != null) {
                writeGroupFooter(writer, groupCount);
            }
            writer.newLine();
            writer.write("Total cities listed: " + rows.size());
            writer.newLine();
        });
    }

    /** Writes the body of a report file. */
    @FunctionalInterface
    private interface ReportBody {
        void write(BufferedWriter writer) throws IOException;
    }

    /**
     * Creates the output file with a title, then writes the body.
     *
     * @throws ReportException if the directory or file cannot be written
     */
    private void writeFile(String fileName, String title, ReportBody body) {
        Path file = outputDirectory.resolve(fileName);
        try {
            Files.createDirectories(outputDirectory);
            try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                writer.write(title);
                writer.newLine();
                writer.write("=".repeat(title.length()));
                writer.newLine();
                body.write(writer);
            }
            System.out.println("Created report: " + file.toAbsolutePath());
        } catch (IOException e) {
            throw new ReportException(
                    "Could not write " + file.toAbsolutePath() + ": " + e.getMessage(), e);
        }
    }

    private static void writeColumnHeader(BufferedWriter writer) throws IOException {
        writer.write(String.format(FILE_ROW_FORMAT, "City", "Country", "District", "Population"));
        writer.newLine();
        writer.write("-".repeat(115));
        writer.newLine();
    }

    private static void writeGroupFooter(BufferedWriter writer, int groupCount) throws IOException {
        writer.write("Cities in this group: " + groupCount);
        writer.newLine();
    }

    private static void writeCity(BufferedWriter writer, City city) throws IOException {
        writer.write(String.format(FILE_ROW_FORMAT,
                shorten(city.getName(), 30),
                shorten(city.getCountry(), 35),
                shorten(city.getDistrict(), 25),
                String.format("%,d", city.getPopulation())));
        writer.newLine();
    }

    /** Truncates a value to fit its column, showing blanks as "Unknown". */
    private static String shorten(String value, int maxLength) {
        if (value == null || value.isBlank()) {
            return "Unknown";
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    private static City mapCity(ResultSet rs) throws SQLException {
        return new City(
                rs.getString("Name"),
                rs.getString("Country"),
                rs.getString("District"),
                rs.getInt("Population"));
    }
}
