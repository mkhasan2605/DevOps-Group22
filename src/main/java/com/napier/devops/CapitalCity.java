package com.napier.devops;

/**
 * Represents a capital city in a population report.
 * Used by UC03 Capital City Reports (R17-R22).
 *
 * @param name       Capital city name, e.g. "Seoul".
 * @param country    Name of the country the city is the capital of.
 * @param population Population of the capital city.
 */
public record CapitalCity(String name, String country, int population) {

    /**
     * Creates a capital city record.
     *
     * @param name       capital city name
     * @param country    country name
     * @param population capital city population
     */
    public CapitalCity {
    }

    /**
     * @return the capital city name
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * @return the country name
     */
    @Override
    public String country() {
        return country;
    }

    /**
     * @return the capital city population
     */
    @Override
    public int population() {
        return population;
    }

    /**
     * Returns a pipe-separated, human-readable representation of the capital city.
     *
     * @return formatted capital city details
     */
    @Override
    public String toString() {
        return String.format("%s | %s | %d", name, country, population);
    }
}
