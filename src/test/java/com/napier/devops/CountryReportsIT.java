package com.napier.devops;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/** Integration tests for CountryReports (R01-R06) against the real database. */
class CountryReportsIT extends DatabaseIT {

    @Test
    void r01_allCountriesReturns239Countries() {
        CountryReports reports = new CountryReports(con);
        ArrayList<Country> countries = reports.getAllCountriesByPopulation();

        assertEquals(239, countries.size(), "R01 should return all 239 countries");

        // Check the first country (largest population: China)
        Country firstCountry = countries.get(0);
        assertEquals("China", firstCountry.getName());

        // Check all getters and toString() to get 100% coverage on Country.java
        assertEquals("CHN", firstCountry.getCode());
        assertEquals("Asia", firstCountry.getContinent());
        assertEquals("Eastern Asia", firstCountry.getRegion());
        assertEquals("Peking", firstCountry.getCapital());
        assertNotNull(firstCountry.toString());
    }

    @Test
    void r02_countriesInAsiaReturns51Countries() {
        CountryReports reports = new CountryReports(con);
        ArrayList<Country> countries = reports.getCountriesByContinent("Asia");

        assertEquals(51, countries.size(), "R02 should return 51 countries in Asia");
    }

    @Test
    void r03_countriesInSoutheastAsiaReturns11Countries() {
        CountryReports reports = new CountryReports(con);
        ArrayList<Country> countries = reports.getCountriesByRegion("Southeast Asia");

        assertEquals(11, countries.size(), "R03 should return 11 countries in Southeast Asia");
    }

    @Test
    void r04_top5CountriesInWorld() {
        CountryReports reports = new CountryReports(con);
        ArrayList<Country> countries = reports.getTopNCountriesWorld(5);

        assertEquals(5, countries.size(), "R04 should return exactly 5 countries");
        assertEquals("China", countries.get(0).getName());

        // Check edge cases (N=0, N=-1) for 100% coverage on this method
        assertTrue(reports.getTopNCountriesWorld(0).isEmpty());
        assertTrue(reports.getTopNCountriesWorld(-1).isEmpty());
    }

    @Test
    void r05_top5CountriesInEurope() {
        CountryReports reports = new CountryReports(con);
        ArrayList<Country> countries = reports.getTopNCountriesContinent("Europe", 5);

        assertEquals(5, countries.size(), "R05 should return exactly 5 countries in Europe");
        assertEquals("Russian Federation", countries.get(0).getName());

        // Check edge cases (N=0, N=-1) for 100% coverage on this method
        assertTrue(reports.getTopNCountriesContinent("Europe", 0).isEmpty());
        assertTrue(reports.getTopNCountriesContinent("Europe", -1).isEmpty());
    }

    @Test
    void r06_top5CountriesInWesternEurope() {
        CountryReports reports = new CountryReports(con);
        ArrayList<Country> countries = reports.getTopNCountriesRegion("Western Europe", 5);

        assertEquals(5, countries.size(), "R06 should return exactly 5 countries in Western Europe");
        assertEquals("Germany", countries.get(0).getName());

        // Check edge cases (N=0, N=-1) for 100% coverage on this method
        assertTrue(reports.getTopNCountriesRegion("Western Europe", 0).isEmpty());
        assertTrue(reports.getTopNCountriesRegion("Western Europe", -1).isEmpty());
    }
}