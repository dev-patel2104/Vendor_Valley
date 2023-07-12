package com.group10.Service;

import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.SignUpModel;
import com.group10.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Qualifier("CustomerProfileService")
public class CustomerProfileService extends ProfileService
{
    @Override
    public void getBookings() {

    }
}
