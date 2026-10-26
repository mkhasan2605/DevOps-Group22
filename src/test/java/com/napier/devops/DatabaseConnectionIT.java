package com.napier.devops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

/** Checks that DatabaseIT connects to a fully loaded world database. */
class DatabaseConnectionIT extends DatabaseIT {

    @Test
    void connectionIsOpen() throws SQLException {
        assertTrue(con.isValid(5));
    }

    @Test
    void worldDatabaseHasAllCities() throws SQLException {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM city")) {
            assertTrue(rs.next());
            assertEquals(4079, rs.getInt(1));
        }
    }
}
