package com.v1.book_nest.controller;

import com.v1.book_nest.exception.ApplicationException;
import com.v1.book_nest.model.UserProfileData;
import com.v1.book_nest.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/ops")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/getUser/{username}")
    public ResponseEntity<UserProfileData> getUserByName(@PathVariable String username) throws ApplicationException {
        UserProfileData user = userDataService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserProfileData> getUserByEmail(@PathVariable String email) throws ApplicationException {
        UserProfileData user = userDataService.getUserByEmailId(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getRoleByUsername/{username}")
    public ResponseEntity<Set<String>> getRoleByUsername(@PathVariable String username) throws ApplicationException {
        Set<String> userRole = userDataService.getRolesByUsername(username);

        if (userRole.isEmpty()) {
            throw new ApplicationException(username + " doesn't have any roles assigned.");
        }
        return ResponseEntity.ok(userRole);
    }

    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<UserProfileData> deleteUser(@PathVariable String username) throws ApplicationException {
        UserProfileData user = userDataService.deleteUser(username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/updateUser/{username}")
    public ResponseEntity<UserProfileData> updateUser(@PathVariable String username, @RequestParam String email) throws ApplicationException {
        UserProfileData user = userDataService.updateUser(username, email);
        return ResponseEntity.ok(user);
    }
}
