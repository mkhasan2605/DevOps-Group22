package com.napier.devops;

/**
 * Represents a city report row containing Name, Country, District, and Population.
 * Part of UC02 (City Reports).
 */
public class City {

    private final String name;
    private final String country;
    private final String district;
    private final int population;

    /**
     * Constructs a new City model instance.
     *
     * @param name       City name
     * @param country    Country name
     * @param district   District name
     * @param population City population
     */
    public City(String name, String country, String district, int population) {
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }

    /**
     * Gets the city name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the country name.
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the district name.
     *
     * @return district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Gets the population of the city.
     *
     * @return population
     */
    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return String.format("%-35s %-35s %-25s %12d",
                name, country, district, population);
    }
}
