package com.napier.devops;

/**
 * Represents a single country row from the world database.
 * Fields match the columns required by the Country Report in the coursework spec.
 */
public class Country {

    /** ISO country code, e.g. "GBR". */
    public String code;

    /** Country name, e.g. "United Kingdom". */
    public String name;

    /** Continent name, e.g. "Europe". */
    public String continent;

    /** Region name, e.g. "British Islands". */
    public String region;

    /** Total population of the country. */
    public int population;

    /** Name of the capital city, or null if the country has no capital. */
    public String capital;
}