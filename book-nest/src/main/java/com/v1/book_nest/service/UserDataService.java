package com.v1.book_nest.service;

import com.v1.book_nest.exception.ApplicationException;
import com.v1.book_nest.model.UserProfileData;

import java.util.Set;

public interface UserDataService {
    //get user by name
    UserProfileData getUserByUsername(String userName) throws ApplicationException;

    //get user by email id
    UserProfileData getUserByEmailId(String email) throws ApplicationException;

    //get user role by username
    Set<String> getRolesByUsername(String userName) throws ApplicationException;

    //delete user by name
    UserProfileData deleteUser(String username) throws ApplicationException;

    //update user
    UserProfileData updateUser(String username,String email) throws ApplicationException;
}
