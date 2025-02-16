package com.usagi.everything.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.usagi.everything.dto.request.NewUserRequest;
import com.usagi.everything.dto.request.UserLoginRequest;
import com.usagi.everything.dto.response.NewUserResponse;
import com.usagi.everything.dto.response.UserLoginResponse;
import com.usagi.everything.security.JwtUtil;
import com.usagi.everything.service.UserService;

@RestController
@RequestMapping("auth/")
public class UserController {
    
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(AuthenticationManager am, UserService userService, JwtUtil jwtUtil) {
        authenticationManager = am;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    //register
    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<NewUserResponse> register(@RequestBody NewUserRequest newUserDetails) {
        try {
            NewUserResponse addedUser = userService.registerUser(newUserDetails);
            addedUser.setToken(jwtUtil.generateToken(addedUser.getUsername()));
            return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
        }
        catch(IllegalArgumentException ex) {
            NewUserResponse failed = new NewUserResponse(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failed);
        }
    }

    //login
    @PostMapping("authenticate")
    @ResponseBody
    public ResponseEntity<UserLoginResponse> authenticate(@RequestBody UserLoginRequest req) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

            userService.handleLastLogin(req.getUsername());

            String message = "User " + req.getUsername() + " has been successfully authenticated!";
            UserLoginResponse resp = new UserLoginResponse(true, jwtUtil.generateToken(req.getUsername()), message);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        catch(BadCredentialsException ex) {
            UserLoginResponse resp = new UserLoginResponse(false, "", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
        }
    }

    //

    //edit account
}
