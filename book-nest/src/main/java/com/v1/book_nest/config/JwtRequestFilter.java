package com.v1.book_nest.config;

import com.v1.book_nest.service.CustomUserDetailService;
import com.v1.book_nest.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username= null;
        String jwtToken= null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            jwtToken= authorizationHeader.substring(7);

            try{
                username= jwtUtil.extractUsername(jwtToken);
            }catch (Exception e){
                System.out.println("JWT token extraction failed: "+ e.getMessage());
            }
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails  userDetails = customUserDetailService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwtToken,userDetails)){
                System.out.println("need to work on it tomorrow");
            }
        }
    }
}
