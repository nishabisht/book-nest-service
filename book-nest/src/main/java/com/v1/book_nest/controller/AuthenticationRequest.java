package com.v1.book_nest.controller;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
