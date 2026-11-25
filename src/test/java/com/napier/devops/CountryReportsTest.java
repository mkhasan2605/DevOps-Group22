package com.napier.devops;

import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Unit tests for CountryReports edge cases and query building. */
class CountryReportsTest {

    private final CountryReports reports = new CountryReports(
            jdbcProxy(Connection.class, (proxy, method, args) -> {
                throw new AssertionError("Unexpected connection call: " + method.getName());
            }));

    @Test
    void topCountriesWorldReturnsEmptyForZeroLimit() { assertTrue(reports.getTopNCountriesWorld(0).isEmpty()); }

    @Test
    void topCountriesWorldReturnsEmptyForNegativeLimit() { assertTrue(reports.getTopNCountriesWorld(-1).isEmpty()); }

    @Test
    void topCountriesContinentReturnsEmptyForZeroLimit() { assertTrue(reports.getTopNCountriesContinent("Asia", 0).isEmpty()); }

    @Test
    void topCountriesContinentReturnsEmptyForNegativeLimit() { assertTrue(reports.getTopNCountriesContinent("Asia", -1).isEmpty()); }

    @Test
    void topCountriesRegionReturnsEmptyForZeroLimit() { assertTrue(reports.getTopNCountriesRegion("Southeast Asia", 0).isEmpty()); }

    @Test
    void topCountriesRegionReturnsEmptyForNegativeLimit() { assertTrue(reports.getTopNCountriesRegion("Southeast Asia", -1).isEmpty()); }

    @Test
    void allCountriesOrdersByPopulation() {
        CountryReports reports = reportsForQuery("ORDER BY country.Population DESC");
        assertMyanmarRow(reports.getAllCountriesByPopulation());
    }

    @Test
    void countriesByContinentBindsContinent() {
        CountryReports reports = reportsForQuery("WHERE country.Continent = ? ORDER BY country.Population DESC", "Asia");
        assertMyanmarRow(reports.getCountriesByContinent("Asia"));
    }

    @Test
    void countriesByRegionBindsRegion() {
        CountryReports reports = reportsForQuery("WHERE country.Region = ? ORDER BY country.Population DESC", "Southeast Asia");
        assertMyanmarRow(reports.getCountriesByRegion("Southeast Asia"));
    }

    @Test
    void topCountriesWorldBindsPositiveLimit() {
        CountryReports reports = reportsForQuery("ORDER BY country.Population DESC LIMIT ?", 1);
        assertMyanmarRow(reports.getTopNCountriesWorld(1));
    }

    @Test
    void topCountriesContinentBindsContinentAndPositiveLimit() {
        CountryReports reports = reportsForQuery("WHERE country.Continent = ? ORDER BY country.Population DESC LIMIT ?", "Asia", 1);
        assertMyanmarRow(reports.getTopNCountriesContinent("Asia", 1));
    }

    @Test
    void topCountriesRegionBindsRegionAndPositiveLimit() {
        CountryReports reports = reportsForQuery("WHERE country.Region = ? ORDER BY country.Population DESC LIMIT ?", "Southeast Asia", 1);
        assertMyanmarRow(reports.getTopNCountriesRegion("Southeast Asia", 1));
    }

    private static CountryReports reportsForQuery(String expectedSqlSuffix, Object... expectedParameters) {
        Map<Integer, Object> boundParameters = new TreeMap<>();
        PreparedStatement statement = jdbcProxy(PreparedStatement.class, (proxy, method, args) -> {
            switch (method.getName()) {
                case "setString": case "setInt": case "setObject":
                    boundParameters.put((Integer) args[0], args[1]);
                    return null;
                case "executeQuery":
                    assertEquals(expectedParameters.length, boundParameters.size());
                    for (int i = 0; i < expectedParameters.length; i++) {
                        assertEquals(expectedParameters[i], boundParameters.get(i + 1), "SQL parameter " + (i + 1));
                    }
                    return myanmarResultSet();
                case "close": return null;
                default: throw new AssertionError("Unexpected statement call: " + method.getName());
            }
        });

        Connection connection = jdbcProxy(Connection.class, (proxy, method, args) -> {
            assertEquals("prepareStatement", method.getName());
            String sql = ((String) args[0]).trim().replaceAll("\\s+", " ");
            assertTrue(sql.contains("LEFT JOIN city ON country.Capital = city.ID"), sql);
            assertTrue(sql.endsWith(expectedSqlSuffix), sql);
            return statement;
        });
        return new CountryReports(connection);
    }

    private static ResultSet myanmarResultSet() {
        Map<String, String> textColumns = Map.of("Code", "MMR", "Name", "Myanmar", "Continent", "Asia", "Region", "Southeast Asia", "Capital", "Yangon");
        return jdbcProxy(ResultSet.class, new InvocationHandler() {
            private boolean rowRead;
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                switch (method.getName()) {
                    case "next":
                        boolean hasRow = !rowRead; rowRead = true; return hasRow;
                    case "getString":
                        assertTrue(textColumns.containsKey(args[0]), "Unexpected column: " + args[0]);
                        return textColumns.get(args[0]);
                    case "getInt":
                        assertEquals("Population", args[0]); return 45_611_000;
                    case "close": return null;
                    default: throw new AssertionError("Unexpected result set call: " + method.getName());
                }
            }
        });
    }

    private static void assertMyanmarRow(List<Country> countries) {
        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("MMR", country.getCode());
        assertEquals("Myanmar", country.getName());
        assertEquals("Asia", country.getContinent());
        assertEquals("Southeast Asia", country.getRegion());
        assertEquals(45_611_000, country.getPopulation());
        assertEquals("Yangon", country.getCapital());
    }

    private static <T> T jdbcProxy(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler));
    }
}