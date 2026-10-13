package com.napier.devops;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Shared JDBC helper for the report classes.
 * Runs a parameterised SELECT and maps each row to an object, so the
 * statement handling and error handling live in one place.
 */
final class QueryRunner {

    /**
     * Maps the current row of a result set to an object.
     *
     * @param <T> type of object produced for each row
     */
    @FunctionalInterface
    interface RowMapper<T> {

        /**
         * Maps the current row.
         *
         * @param rs result set positioned on the row to map
         * @return the mapped object
         * @throws SQLException if a column cannot be read
         */
        T map(ResultSet rs) throws SQLException;
    }

    private QueryRunner() {
    }

    /**
     * Runs a query and maps every row it returns.
     *
     * @param con    open connection to the world database
     * @param sql    query to run, with ? placeholders
     * @param mapper converts each row to an object
     * @param params values for the placeholders, in order
     * @param <T>    type of object produced for each row
     * @return the mapped rows; empty only if the query matched nothing
     * @throws ReportException if there is no connection or the query fails
     */
    static <T> ArrayList<T> query(Connection con, String sql, RowMapper<T> mapper,
            Object... params) {
        if (con == null) {
            throw new ReportException("Not connected to the database", null);
        }
        ArrayList<T> results = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
            }
        } catch (SQLException e) {
            throw new ReportException("Query failed: " + e.getMessage(), e);
        }
        return results;
    }

    /**
     * Checks the N of a "top N" report before it is used as a LIMIT.
     *
     * @param n requested number of rows
     * @return true if n is positive; otherwise prints a message and returns false
     */
    static boolean isValidLimit(int n) {
        if (n <= 0) {
            System.out.println("N must be greater than zero.");
            return false;
        }
        return true;
    }
}
