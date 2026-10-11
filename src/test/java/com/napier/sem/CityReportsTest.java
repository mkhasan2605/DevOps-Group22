
package com.napier.sem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

class CityReportsTest {

    private static final String DB_URL =
            "jdbc:mysql://localhost:33060/world" +
                    "?useSSL=false" +
                    "&allowPublicKeyRetrieval=true" +
                    "&serverTimezone=UTC";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "example";

    @TempDir
    Path temporaryDirectory;

    @Test
    void testAllCityReportsAreGenerated() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD)) {

            CityReports reports =
                    new CityReports(connection, temporaryDirectory);

            reports.runAllReports();
        }

        checkReport("R07_All_Cities.txt",
                "R07 - All Cities in the World");

        checkReport("R08_Cities_By_Continent.txt",
                "R08 - Cities by Continent");

        checkReport("R09_Cities_By_Region.txt",
                "R09 - Cities by Region");

        checkReport("R10_Cities_By_Country.txt",
                "R10 - Cities by Country");

        checkReport("R11_Cities_By_District.txt",
                "R11 - Cities by District");
    }

    @Test
    void testCityGetters() {
        City city = new City(
                "Yangon", "Myanmar", "Yangon", 5209541L);

        assertEquals("Yangon", city.getName());
        assertEquals("Myanmar", city.getCountry());
        assertEquals("Yangon", city.getDistrict());
        assertEquals(5209541L, city.getPopulation());
    }

    private void checkReport(String fileName, String title)
            throws Exception {

        Path reportPath = temporaryDirectory.resolve(fileName);

        assertTrue(Files.exists(reportPath),
                fileName + " should be created.");

        assertTrue(Files.size(reportPath) > 0,
                fileName + " should not be empty.");

        String content = Files.readString(
                reportPath, StandardCharsets.UTF_8);

        assertTrue(content.contains(title),
                fileName + " should contain its report title.");

        assertTrue(content.contains("Name"));
        assertTrue(content.contains("Country"));
        assertTrue(content.contains("District"));
        assertTrue(content.contains("Population"));
        assertTrue(content.contains("Total cities in this report:"));

        assertTrue(content.contains("GROUP:") ||
                        fileName.equals("R07_All_Cities.txt"),
                fileName + " should contain group headings when required.");
    }
}