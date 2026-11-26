package com.napier.devops;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for CountryReports edge cases that do not need the database. */
class CountryReportsTest {

    @Test
    void topCountriesWorldReturnsEmptyForZeroLimit() {
        CountryReports reports = new CountryReports(null);
        assertTrue(reports.getTopNCountriesWorld(0).isEmpty());
    }

    @Test
    void topCountriesWorldReturnsEmptyForNegativeLimit() {
        CountryReports reports = new CountryReports(null);
        assertTrue(reports.getTopNCountriesWorld(-1).isEmpty());
    }

    @Test
    void topCountriesContinentReturnsEmptyForZeroLimit() {
        CountryReports reports = new CountryReports(null);
        assertTrue(reports.getTopNCountriesContinent("Asia", 0).isEmpty());
    }

    @Test
    void topCountriesContinentReturnsEmptyForNegativeLimit() {
        CountryReports reports = new CountryReports(null);
        assertTrue(reports.getTopNCountriesContinent("Asia", -1).isEmpty());
    }

    @Test
    void topCountriesRegionReturnsEmptyForZeroLimit() {
        CountryReports reports = new CountryReports(null);
        assertTrue(reports.getTopNCountriesRegion("Southeast Asia", 0).isEmpty());
    }

    @Test
    void topCountriesRegionReturnsEmptyForNegativeLimit() {
        CountryReports reports = new CountryReports(null);
        assertTrue(reports.getTopNCountriesRegion("Southeast Asia", -1).isEmpty());
    }
}