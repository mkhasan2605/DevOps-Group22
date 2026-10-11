package com.napier.devops;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for CapitalReports (UC03, R17-R22).
 * These need the world database running on localhost:33060, so they are named *IT
 * and are not run by "mvn package".
 */
public class CapitalReportsIT {

    // R17 - All capital cities in the world
    @Test
    public void testAllCapitalCities() throws Exception {

        String url = "jdbc:mysql://localhost:33060/world"
                + "?allowPublicKeyRetrieval=true&useSSL=false";

        try (Connection con = DriverManager.getConnection(
                url, "root", "example")) {

            CapitalReports reports = new CapitalReports(con);

            ArrayList<CapitalCity> capitals =
                    reports.getAllCapitalCities();

            // Check the total number of capitals
            assertEquals(232, capitals.size());

            // Check the first capital city
            assertEquals("Seoul", capitals.get(0).name());
            assertEquals("South Korea", capitals.get(0).country());
            assertEquals(9981619, capitals.get(0).population());

            // Check that all capitals are sorted by population
            for (int i = 0; i < capitals.size() - 1; i++) {
                assertTrue(
                        capitals.get(i).population() >=
                                capitals.get(i + 1).population()
                );
            }
        }
    }

    // R18 - All capital cities in a continent
    @Test
    public void testCapitalCitiesByContinent() throws Exception {

        String url = "jdbc:mysql://localhost:33060/world"
                + "?allowPublicKeyRetrieval=true&useSSL=false";

        try (Connection con = DriverManager.getConnection(
                url, "root", "example")) {

            CapitalReports reports = new CapitalReports(con);

            ArrayList<CapitalCity> capitals =
                    reports.getCapitalCitiesByContinent("Asia");

            // Check the total number of Asian capitals
            assertEquals(51, capitals.size());

            // Check the first capital
            assertEquals("Seoul", capitals.get(0).name());
            assertEquals("South Korea", capitals.get(0).country());
            assertEquals(9981619, capitals.get(0).population());

            // Check descending population order
            for (int i = 0; i < capitals.size() - 1; i++) {
                assertTrue(
                        capitals.get(i).population() >=
                                capitals.get(i + 1).population()
                );
            }
        }
    }
    // R19 - All capital cities in a region
    @Test
    public void testCapitalCitiesByRegion() throws Exception {

        String url = "jdbc:mysql://localhost:33060/world"
                + "?allowPublicKeyRetrieval=true&useSSL=false";

        try (Connection con = DriverManager.getConnection(
                url, "root", "example")) {

            CapitalReports reports = new CapitalReports(con);

            ArrayList<CapitalCity> capitals =
                    reports.getCapitalCitiesByRegion("Southeast Asia");

            assertEquals(11, capitals.size());
            assertEquals("Jakarta", capitals.get(0).name());
            assertEquals("Indonesia", capitals.get(0).country());
            assertEquals(9604900, capitals.get(0).population());

            for (int i = 0; i < capitals.size() - 1; i++) {
                assertTrue(capitals.get(i).population() >=
                        capitals.get(i + 1).population());
            }
        }
    }

    // R20 - Top N capital cities worldwide
    @Test
    public void testTopNCapitalCitiesWorld() throws Exception {

        String url = "jdbc:mysql://localhost:33060/world"
                + "?allowPublicKeyRetrieval=true&useSSL=false";

        try (Connection con = DriverManager.getConnection(
                url, "root", "example")) {

            CapitalReports reports = new CapitalReports(con);

            ArrayList<CapitalCity> capitals =
                    reports.getTopNCapitalCitiesWorld(5);

            assertEquals(5, capitals.size());
            assertEquals("Seoul", capitals.get(0).name());
            assertEquals("South Korea", capitals.get(0).country());
            assertEquals(9981619, capitals.get(0).population());
            assertEquals("Tokyo", capitals.get(4).name());

            for (int i = 0; i < capitals.size() - 1; i++) {
                assertTrue(capitals.get(i).population() >=
                        capitals.get(i + 1).population());
            }
        }
    }

    // R21 - Top N capital cities in a continent
    @Test
    public void testTopNCapitalCitiesContinent() throws Exception {

        String url = "jdbc:mysql://localhost:33060/world"
                + "?allowPublicKeyRetrieval=true&useSSL=false";

        try (Connection con = DriverManager.getConnection(
                url, "root", "example")) {

            CapitalReports reports = new CapitalReports(con);

            ArrayList<CapitalCity> capitals =
                    reports.getTopNCapitalCitiesContinent("Asia", 5);

            assertEquals(5, capitals.size());
            assertEquals("Seoul", capitals.get(0).name());
            assertEquals("South Korea", capitals.get(0).country());
            assertEquals(9981619, capitals.get(0).population());
            assertEquals("Teheran", capitals.get(4).name());

            for (int i = 0; i < capitals.size() - 1; i++) {
                assertTrue(capitals.get(i).population() >=
                        capitals.get(i + 1).population());
            }
        }
    }

    // R22 - Top N capital cities in a region
    @Test
    public void testTopNCapitalCitiesRegion() throws Exception {

        String url = "jdbc:mysql://localhost:33060/world"
                + "?allowPublicKeyRetrieval=true&useSSL=false";

        try (Connection con = DriverManager.getConnection(
                url, "root", "example")) {

            CapitalReports reports = new CapitalReports(con);

            ArrayList<CapitalCity> capitals =
                    reports.getTopNCapitalCitiesRegion("Southeast Asia", 5);

            assertEquals(5, capitals.size());
            assertEquals("Jakarta", capitals.get(0).name());
            assertEquals("Indonesia", capitals.get(0).country());
            assertEquals(9604900, capitals.get(0).population());
            assertEquals("Manila", capitals.get(4).name());

            for (int i = 0; i < capitals.size() - 1; i++) {
                assertTrue(capitals.get(i).population() >=
                        capitals.get(i + 1).population());
            }
        }
    }
}