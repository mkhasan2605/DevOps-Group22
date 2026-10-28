package com.napier.devops;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
}