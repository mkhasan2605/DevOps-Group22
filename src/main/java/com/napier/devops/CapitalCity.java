package com.napier.devops;

/**
 * One row of a capital city report (UC03, R17-R22): Name, Country and Population.
 *
 * @param name       capital city name, e.g. "Seoul"
 * @param country    name of the country the city is the capital of
 * @param population population of the capital city
 */
public record CapitalCity(String name, String country, int population) {

    @Override
    public String toString() {
        return String.format("%s | %s | %d", name, country, population);
    }
}
