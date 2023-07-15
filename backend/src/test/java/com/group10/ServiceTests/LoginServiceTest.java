package com.group10.ServiceTests;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.group10.Exceptions.InvalidPasswordException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.User;
import com.group10.Repository.UserRepository;
import com.group10.Service.LoginService;

@SpringBootTest
public class LoginServiceTest {
    
    @MockBean 
    private UserRepository userRepository;

    @Autowired
    private User user;

    @Autowired
    private LoginService loginService;

    @Test
    public void userFound_login() throws SQLException, UserDoesntExistException, InvalidPasswordException{
        String email = "test@mail.com";
        String password = "password";
        user.setPassword(password);
        Mockito.doReturn(user).when(userRepository).findByEmail(email);
        assertEquals(user, loginService.login(email,password));
    }

    @Test
    public void userFoundPasswordMismatch_login() throws SQLException, UserDoesntExistException, InvalidPasswordException{
        String email = "test@mail.com";
        String password = "password";
        user.setPassword(password+"wrong");
        Mockito.doReturn(user).when(userRepository).findByEmail(email);
        assertThrows(InvalidPasswordException.class, () -> loginService.login(email,password));    
    }
    
    @Test
    public void userNotFound_login() throws SQLException, UserDoesntExistException, InvalidPasswordException{
        String email = "test@mail.com";
        String password = "password";
        Mockito.doReturn(null).when(userRepository).findByEmail(email);
        assertThrows(UserDoesntExistException.class, () -> loginService.login(email,password));    
    }

    @Test
    public void dbConnectionError_login() throws SQLException, UserDoesntExistException, InvalidPasswordException{
        String email = "test@mail.com";
        String password = "password";
        Mockito.doThrow(new SQLException("Database Connection Lost!")).when(userRepository).findByEmail(email);
        assertThrows(SQLException.class, () -> loginService.login(email,password));    
    }
}
