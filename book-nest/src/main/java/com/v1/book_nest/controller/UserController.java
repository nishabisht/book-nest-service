package com.v1.book_nest.controller;

import com.v1.book_nest.exception.ApplicationException;
import com.v1.book_nest.model.UserProfileData;
import com.v1.book_nest.service.UserDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/ops")
public class UserController {
    public static final Logger log= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserDataService userDataService;


    @GetMapping("/getUser/{username}")
    public ResponseEntity<UserProfileData> getUserByName(@PathVariable String username) throws ApplicationException {
        UserProfileData user=userDataService.getUserByUsername(username);

        if(user!=null){
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserProfileData> getUserByEmail(@PathVariable String email) throws ApplicationException {
        UserProfileData user=userDataService.getUserByEmailId(email);

        if(user!=null){
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getRoleByUsername/{username}")
    public ResponseEntity<Set<String>> getRoleByUsername(@PathVariable String username) throws ApplicationException {
        Set<String> userRole=userDataService.getRolesByUsername(username);

        if(userRole == null || userRole.isEmpty()){
            log.warn("user has no role: {}",userRole);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userRole);
        }else {
            log.info("user roles: {}",userRole);
            return ResponseEntity.status(HttpStatus.OK).body(userRole);
        }
    }


    //Endpoint to delete user profile
    //update security file
    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<UserProfileData> deleteUser(@PathVariable String username) throws ApplicationException{
        try{
            UserProfileData user = userDataService.deleteUser(username);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (ApplicationException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping("/updateUser/{username}")
    //Endpoint to update user profile
    public ResponseEntity<UserProfileData> updateUser(@PathVariable String username, @RequestParam String email) throws ApplicationException{
        try{
            UserProfileData user = userDataService.updateUser(username, email);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (ApplicationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
