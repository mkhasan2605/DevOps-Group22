package com.napier.devops;

/**
 * One row of the language report (UC06, R32): how many people speak a language,
 * and what percentage of the world population that is.
 */
public class LanguageReport {

    private final String language;
    private final long speakers;
    private final long worldPopulation;

    /**
     * Creates a language report row.
     *
     * @param language        language name, e.g. "Chinese"
     * @param speakers        number of people who speak the language
     * @param worldPopulation total population of the world
     */
    public LanguageReport(String language, long speakers, long worldPopulation) {
        this.language = language;
        this.speakers = speakers;
        this.worldPopulation = worldPopulation;
    }

    /**
     * Returns the language name.
     *
     * @return the language name
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Returns the number of people who speak the language.
     *
     * @return the number of speakers
     */
    public long getSpeakers() {
        return speakers;
    }

    /**
     * Returns the speakers as a percentage of the world population.
     *
     * @return the percentage, or 0 when the world population is 0
     */
    public double getWorldPercentage() {
        if (worldPopulation <= 0) {
            return 0;
        }
        return speakers * 100.0 / worldPopulation;
    }

    @Override
    public String toString() {
        return String.format("%s | %d | %.2f%%", language, speakers, getWorldPercentage());
    }
}
