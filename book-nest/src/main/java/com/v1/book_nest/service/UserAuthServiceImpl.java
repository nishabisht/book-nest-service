package com.v1.book_nest.service;

import com.v1.book_nest.exception.ApplicationException;
import com.v1.book_nest.model.UserProfileData;
import com.v1.book_nest.repository.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAuthServiceImpl implements UserAuthService{

    public static final Logger log= LoggerFactory.getLogger(UserAuthServiceImpl.class);
    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserProfileData saveUser(UserProfileData userProfileData) throws ApplicationException {
        if(userProfileData==null){
            log.error("User cannot be null");
            throw new ApplicationException("User data is required");
        }
        //Encode the password
        String password = passwordEncoder.encode(userProfileData.getPassword());
        userProfileData.setPassword(password);

        try {
            //Attempt to save the user
           UserProfileData savedUser = userDataRepository.save(userProfileData);
           log.info("User saved successfully with ID: {}",savedUser.getUsername());
           return savedUser;
        } catch (Exception e) {
            throw new ApplicationException("Save operation failed");
        }
    }


}
