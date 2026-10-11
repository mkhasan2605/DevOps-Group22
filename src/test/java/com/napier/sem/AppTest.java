
package com.napier.sem;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static final String DB_URL =
            "jdbc:mysql://localhost:33060/world" +
                    "?useSSL=false" +
                    "&allowPublicKeyRetrieval=true" +
                    "&serverTimezone=UTC";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "example";

    @Test
    void testDatabaseConnection() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD)) {

            assertNotNull(connection);
            assertFalse(connection.isClosed());
        }
    }

    @Test
    void testCityTableContainsRecords() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery("SELECT COUNT(*) FROM city")) {

            assertTrue(resultSet.next());
            assertTrue(resultSet.getLong(1) > 0,
                    "The city table should contain records.");
        }
    }
}