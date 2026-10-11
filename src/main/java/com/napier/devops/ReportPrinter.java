package com.napier.devops;

import java.util.List;

/**
 * Prints report rows as aligned console tables. No database access, so it is easy to unit test.
 */
public class ReportPrinter {

    /**
     * Prints a population report (R23-R25) with a title and column headers.
     *
     * @param title heading printed above the table
     * @param rows  rows to print; null or empty prints a message instead
     */
    public void printPopulation(String title, List<PopulationReport> rows) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        if (rows == null || rows.isEmpty()) {
            System.out.println("No data found.");
            return;
        }
        System.out.printf("%-35s %16s %16s %8s %16s %8s%n",
                "Name", "Population", "In cities", "%", "Not in cities", "%");
        for (PopulationReport r : rows) {
            if (r == null) {
                continue;
            }
            System.out.printf("%-35s %,16d %,16d %7.2f%% %,16d %7.2f%%%n",
                    r.getName(), r.getTotalPopulation(),
                    r.getCityPopulation(), r.getCityPercentage(),
                    r.getNonCityPopulation(), r.getNonCityPercentage());
        }
    }
}
