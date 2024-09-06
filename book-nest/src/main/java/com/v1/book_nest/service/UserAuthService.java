package com.v1.book_nest.service;

import com.v1.book_nest.exception.ApplicationException;
import com.v1.book_nest.model.UserProfileData;

import java.util.Set;

public interface UserAuthService {
    //save user through sign in
    UserProfileData saveUser(UserProfileData userProfileData) throws ApplicationException;






}
