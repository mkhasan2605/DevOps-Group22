package com.napier.devops;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the top-N city reports (R12-R16) in {@link CityReports}.
 * Requires the world database to be running ({@code docker compose up -d db}).
 */
public class CityReportsTopNIT extends DatabaseIT {

    // R12: top N cities in the world
    @Test
    void topNCitiesWorldReturnsLargestCities() {
        CityReports reports = new CityReports(con);

        ArrayList<City> cities = reports.getTopNCitiesWorld(5);

        assertEquals(5, cities.size());
        assertEquals("Mumbai (Bombay)", cities.get(0).getName());
        for (int i = 0; i < cities.size() - 1; i++) {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i + 1).getPopulation());
        }
    }

    // R13: top N cities in a continent
    @Test
    void topNCitiesContinentReturnsLargestCities() {
        CityReports reports = new CityReports(con);

        ArrayList<City> cities = reports.getTopNCitiesContinent("Asia", 5);

        assertEquals(5, cities.size());
        assertEquals("Mumbai (Bombay)", cities.get(0).getName());
        for (int i = 0; i < cities.size() - 1; i++) {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i + 1).getPopulation());
        }
    }

    // R14: top N cities in a region
    @Test
    void topNCitiesRegionReturnsLargestCities() {
        CityReports reports = new CityReports(con);

        ArrayList<City> cities = reports.getTopNCitiesRegion("Western Europe", 5);

        assertEquals(5, cities.size());
        for (int i = 0; i < cities.size() - 1; i++) {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i + 1).getPopulation());
        }
    }

    // R15: top N cities in a country
    @Test
    void topNCitiesCountryReturnsLargestCities() {
        CityReports reports = new CityReports(con);

        ArrayList<City> cities = reports.getTopNCitiesCountry("United Kingdom", 5);

        assertEquals(5, cities.size());
        assertEquals("London", cities.get(0).getName());
        for (int i = 0; i < cities.size() - 1; i++) {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i + 1).getPopulation());
        }
    }
}
