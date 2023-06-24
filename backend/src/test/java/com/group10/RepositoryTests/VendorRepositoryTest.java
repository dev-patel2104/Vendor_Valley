package com.group10.RepositoryTests;

import com.group10.Enums.SignUpVendorSQLQueryEnum;
import com.group10.Service.DatabaseService;
import com.group10.Util.SqlQueries.SQLQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class VendorRepositoryTest {


    @Test
    public void saveVendorTest() throws SQLException { //ref: https://stackoverflow.com/questions/69283087/how-to-write-test-case-for-the-preparedstatement-in-java-using-mockito
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        given(connection.prepareStatement(SQLQuery.insertVendorQuery)).willReturn(preparedStatementMock);
        connection.prepareStatement(SQLQuery.insertVendorQuery).setString(SignUpVendorSQLQueryEnum.COMPANY_CITY.queryIndex, "CityName");

        verify(preparedStatementMock).setString(SignUpVendorSQLQueryEnum.COMPANY_CITY.queryIndex, "CityName");

        when(preparedStatementMock.executeUpdate()).thenReturn(1);
        assertEquals(1, preparedStatementMock.executeUpdate());
    }


}
