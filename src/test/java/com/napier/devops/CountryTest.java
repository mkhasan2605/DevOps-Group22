package com.napier.devops;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the Country model. */
class CountryTest {

    @Test
    void constructorStoresAllFields() {
        Country country = new Country("GBR", "United Kingdom", "Europe", "British Islands", 59623400, "London");

        assertEquals("GBR", country.getCode());
        assertEquals("United Kingdom", country.getName());
        assertEquals("Europe", country.getContinent());
        assertEquals("British Islands", country.getRegion());
        assertEquals(59623400, country.getPopulation());
        assertEquals("London", country.getCapital());
    }

    @Test
    void toStringFormatsAllFieldsCorrectly() {
        Country country = new Country("GBR", "United Kingdom", "Europe", "British Islands", 59623400, "London");
        String expected = "GBR | United Kingdom | Europe | British Islands | 59623400 | London";

        assertEquals(expected, country.toString());
    }

    @Test
    void toStringShowsNAForMissingCapital() {
        Country country = new Country("ATA", "Antarctica", "Antarctica", "Antarctica", 0, null);
        String expected = "ATA | Antarctica | Antarctica | Antarctica | 0 | N/A";

        assertEquals(expected, country.toString());
    }
}