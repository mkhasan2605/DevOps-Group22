package com.napier.devops;

/**
 * One row of a country report (UC01, R01-R06): Code, Name, Continent,
 * Region, Population and Capital.
 */
public class Country {

    private final String code;
    private final String name;
    private final String continent;
    private final String region;
    private final int population;
    private final String capital;

    /**
     * Creates a country report row.
     *
     * @param code       ISO country code, e.g. "GBR"
     * @param name       country name, e.g. "United Kingdom"
     * @param continent  continent name, e.g. "Europe"
     * @param region     region name, e.g. "British Islands"
     * @param population total population of the country
     * @param capital    name of the capital city, or null if the country has none
     */
    public Country(String code, String name, String continent,
                   String region, int population, String capital) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
    }

    /**
     * Returns the ISO country code.
     *
     * @return the ISO country code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the country name.
     *
     * @return the country name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the continent name.
     *
     * @return the continent name
     */
    public String getContinent() {
        return continent;
    }

    /**
     * Returns the region name.
     *
     * @return the region name
     */
    public String getRegion() {
        return region;
    }

    /**
     * Returns the total population.
     *
     * @return the total population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Returns the capital city name, or null if the country has none.
     *
     * @return the capital city name, or null if the country has none
     */
    public String getCapital() {
        return capital;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %d | %s",
                code, name, continent, region, population,
                capital == null ? "N/A" : capital);
    }
}
