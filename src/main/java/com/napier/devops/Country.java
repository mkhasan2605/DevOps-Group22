package com.napier.devops;

/**
 * Represents a single country row from the world database.
 * Fields match the columns required by the Country Report in the coursework spec.
 */
public class Country {

    /** ISO country code, e.g. "GBR". */
    private String code;

    /** Country name, e.g. "United Kingdom". */
    private String name;

    /** Continent name, e.g. "Europe". */
    private String continent;

    /** Region name, e.g. "British Islands". */
    private String region;

    /** Total population of the country. */
    private int population;

    /** Name of the capital city, or null if the country has no capital. */
    private String capital;

    /** @return the ISO country code */
    public String getCode() { return code; }

    /** @param code the ISO country code to set */
    public void setCode(String code) { this.code = code; }

    /** @return the country name */
    public String getName() { return name; }

    /** @param name the country name to set */
    public void setName(String name) { this.name = name; }

    /** @return the continent name */
    public String getContinent() { return continent; }

    /** @param continent the continent name to set */
    public void setContinent(String continent) { this.continent = continent; }

    /** @return the region name */
    public String getRegion() { return region; }

    /** @param region the region name to set */
    public void setRegion(String region) { this.region = region; }

    /** @return the total population */
    public int getPopulation() { return population; }

    /** @param population the total population to set */
    public void setPopulation(int population) { this.population = population; }

    /** @return the capital city name, or null if none */
    public String getCapital() { return capital; }

    /** @param capital the capital city name to set */
    public void setCapital(String capital) { this.capital = capital; }
}