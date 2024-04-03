package com.jwt.mybatis.jwtApplication.controller;

import com.jwt.mybatis.jwtApplication.dto.JwtResponse;
import com.jwt.mybatis.jwtApplication.dto.LoginRequest;
import com.jwt.mybatis.jwtApplication.security.JwtHelper;
import com.jwt.mybatis.jwtApplication.security.JwtHelperNimbus;
import com.jwt.mybatis.jwtApplication.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelperNimbus jwtHelper;

    @PostMapping("/login")
    public JwtResponse loginUser(@RequestBody LoginRequest request) {
        try {
            this.doAuthenticate(request.getUsername(), request.getPassword());

            UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
            String token = this.jwtHelper.generateToken(userDetails);

            JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();
            return response;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(handleBadCredentialsException());
        }
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleBadCredentialsException() {
        return "Invalid Username or Password";
    }

}

