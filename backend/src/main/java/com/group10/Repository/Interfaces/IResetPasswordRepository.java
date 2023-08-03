package com.group10.Repository.Interfaces;

import java.sql.SQLException;

public interface IResetPasswordRepository
{
    public boolean storeVerificationCode(int id, int code) throws SQLException;
    public int getVerificationCode(String email) throws SQLException;
}
