package com.napier.devops;

import java.util.List;

/**
 * Prints report rows as aligned console tables. It has no database access,
 * so the report classes only fetch data and this class only formats it.
 */
public class ReportPrinter {

    private static final int NAME_WIDTH = 44;

    /**
     * Prints a country report (R01-R06).
     *
     * @param title     heading printed above the table
     * @param countries rows to print; null or empty prints a message instead
     */
    public void printCountries(String title, List<Country> countries) {
        if (!printTitle(title, countries)) {
            return;
        }
        String format = "%-4s  %-" + NAME_WIDTH + "s  %-13s  %-25s  %15s  %s%n";
        System.out.printf(format, "Code", "Name", "Continent", "Region", "Population", "Capital");
        for (Country c : countries) {
            System.out.printf(format, c.getCode(), c.getName(), c.getContinent(), c.getRegion(),
                    String.format("%,d", c.getPopulation()),
                    c.getCapital() == null ? "N/A" : c.getCapital());
        }
        printTotal(countries.size());
    }

    /**
     * Prints a city report (R12-R16).
     *
     * @param title  heading printed above the table
     * @param cities rows to print; null or empty prints a message instead
     */
    public void printCities(String title, List<City> cities) {
        if (!printTitle(title, cities)) {
            return;
        }
        String format = "%-35s  %-" + NAME_WIDTH + "s  %-25s  %15s%n";
        System.out.printf(format, "Name", "Country", "District", "Population");
        for (City c : cities) {
            System.out.printf(format, c.getName(), c.getCountry(), c.getDistrict(),
                    String.format("%,d", c.getPopulation()));
        }
        printTotal(cities.size());
    }

    /**
     * Prints a capital city report (R17-R22).
     *
     * @param title    heading printed above the table
     * @param capitals rows to print; null or empty prints a message instead
     */
    public void printCapitals(String title, List<CapitalCity> capitals) {
        if (!printTitle(title, capitals)) {
            return;
        }
        String format = "%-35s  %-" + NAME_WIDTH + "s  %15s%n";
        System.out.printf(format, "Name", "Country", "Population");
        for (CapitalCity c : capitals) {
            System.out.printf(format, c.name(), c.country(), String.format("%,d", c.population()));
        }
        printTotal(capitals.size());
    }

    /**
     * Prints a population breakdown report (R23-R25).
     *
     * @param title heading printed above the table
     * @param rows  rows to print; null or empty prints a message instead
     */
    public void printPopulation(String title, List<PopulationReport> rows) {
        if (!printTitle(title, rows)) {
            return;
        }
        System.out.printf("%-" + NAME_WIDTH + "s  %16s  %16s  %8s  %16s  %8s%n",
                "Name", "Population", "In cities", "%", "Not in cities", "%");
        for (PopulationReport r : rows) {
            System.out.printf("%-" + NAME_WIDTH + "s  %,16d  %,16d  %7.2f%%  %,16d  %7.2f%%%n",
                    r.getName(), r.getTotalPopulation(),
                    r.getCityPopulation(), r.getCityPercentage(),
                    r.getNonCityPopulation(), r.getNonCityPercentage());
        }
        printTotal(rows.size());
    }

    /**
     * Prints the population of a single named place (R27-R29).
     *
     * @param title heading printed above the result
     * @param place the place to print; null prints a message instead
     */
    public void printPlacePopulation(String title, PlacePopulation place) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        if (place == null) {
            System.out.println("No data found.");
            return;
        }
        System.out.printf("%-" + NAME_WIDTH + "s  %,16d%n", place.getName(), place.getPopulation());
    }

    /**
     * Prints the report heading, or a "no data" message when there is nothing to show.
     *
     * @return true if there are rows to print
     */
    private boolean printTitle(String title, List<?> rows) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        if (rows == null || rows.isEmpty()) {
            System.out.println("No data found.");
            return false;
        }
        return true;
    }

    private void printTotal(int count) {
        System.out.println("Rows: " + count);
    }
}
