package com.napier.devops;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for printing city reports in ReportPrinter.
 * Verifies that null, empty, and valid city lists print without throwing exceptions.
 */
public class ReportPrinterCityTest {

    @Test
    void printCitiesWithNullListDoesNotThrow() {
        ReportPrinter printer = new ReportPrinter();
        assertDoesNotThrow(() -> printer.printCities("Test", null));
    }

    @Test
    void printCitiesWithEmptyListDoesNotThrow() {
        ReportPrinter printer = new ReportPrinter();
        assertDoesNotThrow(() -> printer.printCities("Test", List.of()));
    }

    @Test
    void printCitiesWithValidListDoesNotThrow() {
        ReportPrinter printer = new ReportPrinter();
        List<City> cities = List.of(
                new City("London", "United Kingdom", "England", 7285000),
                new City("Tokyo", "Japan", "Tokyo-to", 7980230)
        );
        assertDoesNotThrow(() -> printer.printCities("Test", cities));
    }
}
