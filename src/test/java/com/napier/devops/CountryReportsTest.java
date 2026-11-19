package com.napier.devops;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CountryTest {

    // Invalid limits should return before any database connection method is called.
    private final CountryReports reports = new CountryReports(
            (Connection) Proxy.newProxyInstance(
                    Connection.class.getClassLoader(),
                    new Class<?>[]{Connection.class},
                    (proxy, method, args) -> {
                        throw new AssertionError("Unexpected database call: " + method.getName());
                    }));

    @Test
    void topCountriesWorldReturnsEmptyForZeroLimit() {
        assertTrue(reports.getTopNCountriesWorld(0).isEmpty());
    }

    @Test
    void topCountriesWorldReturnsEmptyForNegativeLimit() {
        assertTrue(reports.getTopNCountriesWorld(-1).isEmpty());
    }

    @Test
    void topCountriesContinentReturnsEmptyForZeroLimit() {
        assertTrue(reports.getTopNCountriesContinent("Asia", 0).isEmpty());
    }

    @Test
    void topCountriesContinentReturnsEmptyForNegativeLimit() {
        assertTrue(reports.getTopNCountriesContinent("Asia", -1).isEmpty());
    }

    @Test
    void topCountriesRegionReturnsEmptyForZeroLimit() {
        assertTrue(reports.getTopNCountriesRegion("Southeast Asia", 0).isEmpty());
    }

    @Test
    void topCountriesRegionReturnsEmptyForNegativeLimit() {
        assertTrue(reports.getTopNCountriesRegion("Southeast Asia", -1).isEmpty());
    }
}
