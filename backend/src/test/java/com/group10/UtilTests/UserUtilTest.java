package com.group10.UtilTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.group10.Model.User;
import com.group10.Util.MapResultSetUtil;

@SpringBootTest
public class UserUtilTest {

    @Autowired
    private MapResultSetUtil userUtil;
    
    @Autowired
    private User user;
    
    @Test
    public void testMapResultSetToUser() throws SQLException { 
        // Create a mock ResultSet
        ResultSet resultSet = mock(ResultSet.class);

        // Specify the behavior of the mock ResultSet
        when(resultSet.findColumn("user_id")).thenReturn(1);
        when(resultSet.getObject(1)).thenReturn(1);
        when(resultSet.getString("last_name")).thenReturn("Doe");
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("mobile")).thenReturn("1234567890");
        when(resultSet.getInt("is_vendor")).thenReturn(0);
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");
        when(resultSet.getString("street")).thenReturn("123 Main St");
        when(resultSet.getString("city")).thenReturn("Springfield");
        when(resultSet.getString("province")).thenReturn("IL");
        when(resultSet.getString("country")).thenReturn("USA");
        when(resultSet.getString("password")).thenReturn("password");

        // Call the method under test
        user = userUtil.mapResultSetToUser(resultSet);

        // Verify the results
        assertEquals(1, user.getUserId());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("1234567890", user.getMobile());
        assertEquals(0, user.getVendor());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("123 Main St", user.getStreet());
        assertEquals("Springfield", user.getCity());
        assertEquals("IL", user.getProvince());
        assertEquals("USA", user.getCountry());
        assertEquals("password", user.getPassword());
    }
}
