package com.v1.book_nest.controller;

import com.v1.book_nest.exception.ApplicationException;
import com.v1.book_nest.model.UserProfileData;
import com.v1.book_nest.service.UserAuthService;
import com.v1.book_nest.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public static final Logger log= LoggerFactory.getLogger(AuthenticationController.class);

    //Endpoint to Save & get api
    @PostMapping("/saveUser")
    public ResponseEntity<UserProfileData> saveUser(@RequestBody UserProfileData userProfileData) throws ApplicationException {
        try{
            UserProfileData savedUser = userAuthService.saveUser(userProfileData);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (ApplicationException e){
            throw new ApplicationException("User registration failed"+ e.getMessage());
        }
    }


    //Endpoint to authentication api
   @PostMapping("/login")
   public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
       //Authenticate the user using their username and password
      try {
          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
          );
          log.info("User authenticated : {}", authenticationRequest.getUsername());
      }catch (AuthenticationException e){
          log.warn("Failed login attempt for user : {}", authenticationRequest.getUsername());
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
      }

      //Load the user details
       final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
      //Generate a JWT token for the user
       final String jwtToken= jwtUtil.generateToken(userDetails);

       //Return the JWT token in the response
       return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
   }



}
