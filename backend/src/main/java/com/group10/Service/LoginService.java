package com.group10.Service;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group10.Exceptions.InvalidPasswordException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.User;
import com.group10.Repository.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private User user;

    public User login(String email, String password) throws UserDoesntExistException, InvalidPasswordException, SQLException {
        try{
            user = userRepository.findByEmail(email);
        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        if (user == null) {
            throw new UserDoesntExistException("User Doesn't Exists!");
        }
        if (!user.getPassword().equalsIgnoreCase(password)){
            throw new InvalidPasswordException("Wrong Password Entered!");
        }
        return user;
    }
}
