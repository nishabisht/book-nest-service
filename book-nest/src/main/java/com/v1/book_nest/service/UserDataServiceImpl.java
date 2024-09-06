package com.v1.book_nest.service;


import com.v1.book_nest.exception.ApplicationException;
import com.v1.book_nest.model.UserProfileData;
import com.v1.book_nest.repository.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDataServiceImpl implements UserDataService {
    public static final Logger log= LoggerFactory.getLogger(UserDataServiceImpl.class);
    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserProfileData getUserByUsername(String userName) throws ApplicationException {
        UserProfileData user=userDataRepository.findByUsername(userName).orElseThrow(()->new ApplicationException("User not found with username: {}"+userName));
        return user;
    }

    @Override
    public UserProfileData getUserByEmailId(String email) throws ApplicationException {
        UserProfileData user=userDataRepository.findByEmailId(email).orElseThrow(()-> new ApplicationException("User not found with email: {}"+email));
        return user;
    }

    @Override
    public Set<String> getRolesByUsername(String userName) throws ApplicationException {
        UserProfileData user=userDataRepository.findByUsername(userName).orElseThrow(()->new ApplicationException("User not found"+userName));
        Set<String> role=user.getRoles();
        if (role.isEmpty()){
            log.info("user don't have any role assign: {}",role);
        } else {
            log.info("user roles : {}",role);
        }
        return role;
    }

    @Override
    public UserProfileData deleteUser(String username) throws ApplicationException {
        UserProfileData user=null;
        user=userDataRepository.findByUsername(username)
                .orElseThrow(()->new ApplicationException("User not found with username :"+username));

        if(user.getRoles().contains("admin")){
            userDataRepository.delete(user);
            log.info("Admin user {} deleted successfully", username);
        }else {
            log.error("User {} does not have admin role, deletion not permitted",username);
            throw new ApplicationException("Deletion not allowed: user does not have admin access.");
        }
        return user;
    }

    @Override
    public UserProfileData updateUser(String username,String email) throws ApplicationException {
        UserProfileData user=userDataRepository.findByUsername(username).orElseThrow(()->new ApplicationException("User not found with username : "+username));
        log.info("Updating email for user: {}", username);

        if(email!=null) {
            user.setEmailId(email);
        }else{
            throw new ApplicationException("Email can not be null or empty");
        }
        log.info("New email: {}", email);
        return userDataRepository.save(user);
    }
}
