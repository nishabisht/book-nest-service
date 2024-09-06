package com.v1.book_nest.controller;

public class AuthenticationResponse {
    private String jwt=null;

    public  AuthenticationResponse(String jwt){
        this.jwt=jwt;
    }

    public String getJwt(){
        return jwt;
    }
}
