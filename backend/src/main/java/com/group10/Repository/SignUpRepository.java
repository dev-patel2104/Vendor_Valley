package com.group10.Repository;

import com.group10.Enums.SignUpVendorSQLQueryEnum;
import com.group10.Model.SignUpModel;
import com.group10.Service.DatabaseService;
import com.group10.Util.SqlQueries.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



@Repository
public class SignUpRepository {

    @Autowired
    DatabaseService databaseConfig;

    public boolean saveVendor(SignUpModel signUpModel) {
        Connection connection = databaseConfig.connect();
        try {
            PreparedStatement sqlPreparedStatement = connection.prepareStatement(SQLQuery.insertVendorQuery);

            prepareInsertVendorQuery(sqlPreparedStatement, signUpModel);

            int noOfRowsAffected = sqlPreparedStatement.executeUpdate();

            if (noOfRowsAffected == 0) return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private static void prepareInsertVendorQuery(PreparedStatement sqlPreparedStatement, SignUpModel signUpModel) throws SQLException {
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_CITY.queryIndex, signUpModel.getCompanyCity());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_COUNTRY.queryIndex, signUpModel.getCompanyCountry());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_EMAIL.queryIndex, signUpModel.getCompanyEmail());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_MOBILE.queryIndex, signUpModel.getCompanyMobile());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_NAME.queryIndex, signUpModel.getCompanyName());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_PROVINCE.queryIndex, signUpModel.getCompanyProvince());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_REGISTRATION_ID.queryIndex, signUpModel.getCompanyRegistrationID());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_STREET.queryIndex, signUpModel.getCompanyStreet());
    }
}
