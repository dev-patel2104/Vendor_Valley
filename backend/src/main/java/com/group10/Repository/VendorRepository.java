package com.group10.Repository;

import com.group10.Enums.SignUpVendorSQLQueryEnum;
import com.group10.Model.Vendor;
import com.group10.Service.DatabaseService;
import com.group10.Util.SqlQueries.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
public class VendorRepository {


    @Autowired
    DatabaseService databaseService;

    public boolean saveVendor(Vendor vendorModel) {
        try (Connection connection = databaseService.connect();
             PreparedStatement sqlPreparedStatement = connection.prepareStatement(SQLQuery.insertVendorQuery)) {

            prepareInsertVendorQuery(sqlPreparedStatement, vendorModel);

            int noOfRowsAffected = sqlPreparedStatement.executeUpdate();

            if (noOfRowsAffected == 0) return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private static void prepareInsertVendorQuery(PreparedStatement sqlPreparedStatement, Vendor vendorModel) throws SQLException {
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_CITY.queryIndex, vendorModel.getCompanyCity());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_COUNTRY.queryIndex, vendorModel.getCompanyCountry());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_EMAIL.queryIndex, vendorModel.getCompanyEmail());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_MOBILE.queryIndex, vendorModel.getCompanyMobile());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_NAME.queryIndex, vendorModel.getCompanyName());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_PROVINCE.queryIndex, vendorModel.getCompanyProvince());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_REGISTRATION_ID.queryIndex, vendorModel.getCompanyRegistrationID());
        sqlPreparedStatement.setString(SignUpVendorSQLQueryEnum.COMPANY_STREET.queryIndex, vendorModel.getCompanyStreet());
    }
}
