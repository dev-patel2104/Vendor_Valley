package com.group10.Service.Interfaces;

import java.sql.Connection;

/**
 * Interface for providing database connection functionality.
 */
public interface IDatabaseService {

    /**
     * Establishes a database connection.
     *
     * @return A Connection object representing the database connection.
     */
    Connection connect();
}
