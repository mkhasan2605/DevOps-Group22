package com.napier.devops;
/**
 * Represents a named place and its population.
 * Used for population reports R27-R31.
 */
public class PlacePopulation {    private String name;
    private long population;

    public PlacePopulation(String name, long population) {
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public long getPopulation() {
        return population;
    }
}
