package com.napier.devops;

/**
 * Represents a capital city in a population report.
 * Used by UC03 Capital City Reports (R17-R22).
 */
public class CapitalCity {

    public String name;
    public String country;
    public int population;

    /**
     * Creates a capital city record.
     *
     * @param name Capital city name
     * @param country Country name
     * @param population Capital city population
     */
    public CapitalCity(String name, String country, int population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }
}