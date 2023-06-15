package com.vendorvalley_api.vendorvalley_api.repository;

import com.vendorvalley_api.vendorvalley_api.databaseConnector.DatabaseConnector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
public class SignUpRepositoryTest {

    @Mock
    DatabaseConnector databaseConnector;

    @Test
    public void saveVendorTest() {
        assertNotNull(databaseConnector);

        Connection connection = databaseConnector.connect();
    }


}
