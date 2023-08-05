package com.group10.Repository.Interfaces;

import java.sql.SQLException;

/**
 * An interface that provides methods for storing and retrieving verification codes
 * for resetting passwords.
 */
public interface IResetPasswordRepository
{
    /**
     * Stores a verification code for a user.
     *
     * @param id The user's identification.
     * @param code The verification code to be stored.
     * @return true if the verification code is successfully stored, false otherwise.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public boolean storeVerificationCode(int id, int code) throws SQLException;

    /**
     * Retrieves the verification code associated with a user's email.
     *
     * @param email The user's email.
     * @return The verification code retrieved from the database.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public int getVerificationCode(String email) throws SQLException;
}

