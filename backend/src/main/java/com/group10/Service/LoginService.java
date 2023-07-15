package com.group10.Service;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group10.Exceptions.InvalidPasswordException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.User;
import com.group10.Repository.UserRepository;

/**
 * Service class for handling user login functionality.
 */
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private User user;

    /**
     * Logs in a user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The logged in user.
     * @throws UserDoesntExistException If the user with the given email doesn't exist.
     * @throws InvalidPasswordException If the password entered is incorrect.
     * @throws SQLException If there is an error while accessing the database.
     */
    public User login(String email, String password) throws UserDoesntExistException, InvalidPasswordException, SQLException {
        try{
            /**
             * Retrieves a user from the user repository based on their email.
             *
             * @param email The email of the user to retrieve.
             * @return The user entity associated with the given email, or null if no user is found.
             */
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
