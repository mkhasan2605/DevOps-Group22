package com.napier.devops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base class for integration tests. Opens one connection to the world database
 * before a test class runs and closes it afterwards; subclasses use {@link #con}.
 * The database must be running first: {@code docker compose up -d db}.
 * Location and credentials default to localhost:33060 and root/example, and can be
 * overridden with the DB_LOCATION, DB_USER and DB_PASSWORD environment variables.
 */
public abstract class DatabaseIT {

    private static final String LOCATION =
            System.getenv().getOrDefault("DB_LOCATION", "localhost:33060");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "example");

    protected static Connection con;

    /**
     * Connects to the world database.
     *
     * @throws SQLException if the database cannot be reached
     */
    @BeforeAll
    static void connect() throws SQLException {
        con = DriverManager.getConnection(
                "jdbc:mysql://" + LOCATION + "/world?sslMode=DISABLED&allowPublicKeyRetrieval=true",
                USER, PASSWORD);
    }

    /**
     * Closes the connection.
     *
     * @throws SQLException if closing fails
     */
    @AfterAll
    static void disconnect() throws SQLException {
        if (con != null) {
            con.close();
            con = null;
        }
    }
}
