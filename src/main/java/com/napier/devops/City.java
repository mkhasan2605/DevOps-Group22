package com.napier.devops;

/**
 * One row of a city report (UC02, R07-R16): Name, Country, District and Population.
 */
public class City {

    private final String name;
    private final String country;
    private final String district;
    private final int population;

    /**
     * Creates a city report row.
     *
     * @param name       city name
     * @param country    name of the country the city is in
     * @param district   district name
     * @param population city population
     */
    public City(String name, String country, String district, int population) {
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }

    /**
     * Returns the city name.
     *
     * @return the city name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the country name.
     *
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the district name.
     *
     * @return the district name
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Returns the city population.
     *
     * @return the city population
     */
    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %d", name, country, district, population);
    }
}
