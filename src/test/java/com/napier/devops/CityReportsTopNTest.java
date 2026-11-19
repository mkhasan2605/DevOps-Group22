package com.napier.devops;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the top N city reports (R12-R16) in CityReports.
 * These tests do NOT need the database. We give CityReports a null connection
 * on purpose, so we can check how it behaves with bad input
 * and with no database.
 */
public class CityReportsTopNTest {

    // R12: top N cities in the world
    @Test
    void topNCitiesWorldReturnsEmptyListForZeroAndNegative() {
        CityReports reports = new CityReports(null);

        ArrayList<City> zeroResult = reports.getTopNCitiesWorld(0);
        ArrayList<City> negativeResult = reports.getTopNCitiesWorld(-1);

        assertTrue(zeroResult.isEmpty());
        assertTrue(negativeResult.isEmpty());
    }

    // R13: top N cities in a continent
    @Test
    void topNCitiesContinentReturnsEmptyListForZeroAndNegative() {
        CityReports reports = new CityReports(null);

        ArrayList<City> zeroResult = reports.getTopNCitiesContinent("Asia", 0);
        ArrayList<City> negativeResult = reports.getTopNCitiesContinent("Asia", -1);

        assertTrue(zeroResult.isEmpty());
        assertTrue(negativeResult.isEmpty());
    }

    // R14: top N cities in a region
    @Test
    void topNCitiesRegionReturnsEmptyListForZeroAndNegative() {
        CityReports reports = new CityReports(null);

        ArrayList<City> zeroResult = reports.getTopNCitiesRegion("Western Europe", 0);
        ArrayList<City> negativeResult = reports.getTopNCitiesRegion("Western Europe", -1);

        assertTrue(zeroResult.isEmpty());
        assertTrue(negativeResult.isEmpty());
    }

    // R15: top N cities in a country
    @Test
    void topNCitiesCountryReturnsEmptyListForZeroAndNegative() {
        CityReports reports = new CityReports(null);

        ArrayList<City> zeroResult = reports.getTopNCitiesCountry("United Kingdom", 0);
        ArrayList<City> negativeResult = reports.getTopNCitiesCountry("United Kingdom", -1);

        assertTrue(zeroResult.isEmpty());
        assertTrue(negativeResult.isEmpty());
    }

    // R16: top N cities in a district
    @Test
    void topNCitiesDistrictReturnsEmptyListForZeroAndNegative() {
        CityReports reports = new CityReports(null);

        ArrayList<City> zeroResult = reports.getTopNCitiesDistrict("England", 0);
        ArrayList<City> negativeResult = reports.getTopNCitiesDistrict("England", -1);

        assertTrue(zeroResult.isEmpty());
        assertTrue(negativeResult.isEmpty());
    }

    // N = 5 is a valid number, so the method tries to use the database.
    // With a null connection there is no database, so each method must
    // throw a ReportException. assertThrows checks that this happens.

    @Test
    void topNCitiesWorldThrowsWhenThereIsNoConnection() {
        CityReports reports = new CityReports(null);

        assertThrows(ReportException.class, () -> reports.getTopNCitiesWorld(5));
    }

    @Test
    void topNCitiesContinentThrowsWhenThereIsNoConnection() {
        CityReports reports = new CityReports(null);

        assertThrows(ReportException.class, () -> reports.getTopNCitiesContinent("Asia", 5));
    }

    @Test
    void topNCitiesRegionThrowsWhenThereIsNoConnection() {
        CityReports reports = new CityReports(null);

        assertThrows(ReportException.class, () -> reports.getTopNCitiesRegion("Western Europe", 5));
    }

    @Test
    void topNCitiesCountryThrowsWhenThereIsNoConnection() {
        CityReports reports = new CityReports(null);

        assertThrows(ReportException.class, () -> reports.getTopNCitiesCountry("United Kingdom", 5));
    }

    @Test
    void topNCitiesDistrictThrowsWhenThereIsNoConnection() {
        CityReports reports = new CityReports(null);

        assertThrows(ReportException.class, () -> reports.getTopNCitiesDistrict("England", 5));
    }
}