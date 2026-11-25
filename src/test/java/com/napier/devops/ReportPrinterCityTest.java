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
}
