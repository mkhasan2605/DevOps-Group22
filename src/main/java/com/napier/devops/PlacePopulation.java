package com.napier.devops;

/**
 * The population of a named place: a continent, region or country (R27-R29).
 */
public class PlacePopulation {

    private final String name;
    private final long population;

    /**
     * Creates a named place population row.
     *
     * @param name       name of the continent, region or country
     * @param population total population of the place
     */
    public PlacePopulation(String name, long population) {
        this.name = name;
        this.population = population;
    }

    /**
     * Returns the name of the continent, region or country.
     *
     * @return name of the continent, region or country
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the total population of the place.
     *
     * @return total population of the place
     */
    public long getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return String.format("%s | %d", name, population);
    }
}
