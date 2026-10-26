package com.napier.devops;

import java.sql.Connection;
import java.util.List;

/**
 * UC06 language report (R32): the number of people who speak Chinese, English, Hindi,
 * Spanish and Arabic, and each as a percentage of the world population, largest first.
 * Speakers in a country are its population multiplied by the language's percentage there.
 */
public class LanguageReports {

    /** The languages required by R32. */
    public static final List<String> LANGUAGES =
            List.of("Chinese", "English", "Hindi", "Spanish", "Arabic");

    private static final String QUERY = """
            SELECT cl.Language,
                   ROUND(SUM(c.Population * cl.Percentage / 100)) AS Speakers,
                   (SELECT SUM(Population) FROM country) AS WorldPopulation
            FROM countrylanguage cl JOIN country c ON cl.CountryCode = c.Code
            WHERE cl.Language IN (?, ?, ?, ?, ?)
            GROUP BY cl.Language
            ORDER BY Speakers DESC
            """;

    private final Connection con;

    /**
     * Creates the report runner.
     *
     * @param con open connection to the world database
     */
    public LanguageReports(Connection con) {
        this.con = con;
    }

    /**
     * R32: speakers of Chinese, English, Hindi, Spanish and Arabic.
     *
     * @return one row per language, most speakers first
     */
    public List<LanguageReport> getLanguageSpeakers() {
        return QueryRunner.query(con, QUERY,
                rs -> new LanguageReport(
                        rs.getString("Language"),
                        rs.getLong("Speakers"),
                        rs.getLong("WorldPopulation")),
                LANGUAGES.toArray());
    }
}
