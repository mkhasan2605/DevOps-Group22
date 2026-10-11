package com.napier.devops;

/**
 * One row of a population report (R23-R25): the population of a continent, region or country,
 * split into people living in cities and people not living in cities.
 */
public class PopulationReport {

    /** Name of the continent, region or country. */
    private final String name;

    /** Total population. */
    private final long totalPopulation;

    /** Population living in cities. */
    private final long cityPopulation;

    /**
     * Creates a population report row.
     *
     * @param name            name of the continent, region or country
     * @param totalPopulation total population
     * @param cityPopulation  population living in cities
     */
    public PopulationReport(String name, long totalPopulation, long cityPopulation) {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = cityPopulation;
    }

    /** @return name of the continent, region or country */
    public String getName() {
        return name;
    }

    /** @return total population */
    public long getTotalPopulation() {
        return totalPopulation;
    }

    /** @return population living in cities */
    public long getCityPopulation() {
        return cityPopulation;
    }

    /**
     * Population not living in cities. Never negative: a few countries in the world database
     * (e.g. Singapore) list more people in cities than in the whole country.
     *
     * @return total population minus city population, minimum 0
     */
    public long getNonCityPopulation() {
        return Math.max(0, totalPopulation - cityPopulation);
    }

    /**
     * Percentage of the population living in cities, capped at 100.
     *
     * @return city percentage, or 0 when the total population is 0 (e.g. Antarctica)
     */
    public double getCityPercentage() {
        if (totalPopulation <= 0) {
            return 0;
        }
        return Math.min(100.0, cityPopulation * 100.0 / totalPopulation);
    }

    /**
     * Percentage of the population not living in cities.
     *
     * @return non-city percentage, or 0 when the total population is 0
     */
    public double getNonCityPercentage() {
        if (totalPopulation <= 0) {
            return 0;
        }
        return 100.0 - getCityPercentage();
    }

    @Override
    public String toString() {
        return "PopulationReport{name='" + name + "', totalPopulation=" + totalPopulation
                + ", cityPopulation=" + cityPopulation + "}";
    }
}
