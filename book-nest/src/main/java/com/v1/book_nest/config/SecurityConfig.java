package com.v1.book_nest.config;

import com.v1.book_nest.exception.CustomAccessDeniedHandler;
import com.v1.book_nest.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private final JwtRequestFilter jwtRequestFilter;
    @Autowired
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;


    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, CustomUserDetailService customUserDetailService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                        .requestMatchers("/ops/getUserByEmail/{email}","/ops/getUser/{username}","/ops/getRoleByUsername/{username}").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/ops/deleteUser/{username}").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/ops/updateUser/{username}").authenticated()
                        .requestMatchers(HttpMethod.POST,"/auth/saveUser","/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .sessionManagement(session-> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions().sameOrigin());

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
