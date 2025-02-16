package com.usagi.everything.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
import com.usagi.everything.security.JwtUtil;
import com.usagi.everything.service.UserService;

@RestController
@RequestMapping("auth/")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager am, UserService userService, JwtUtil jwtUtil) {
        authenticationManager = am;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint for registering a new user
     * @param newUserDetails User account details that will be used to create the new user
     * @return Registration success = Status 200, sets JWT as httpOnly cookie, success message
     * @return Registration failure = Status 400, failure message
     */
    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody NewUserRequest newUserDetails) {
        try {
            userService.registerUser(newUserDetails);

            String message = "User registered successfully!";
            String jwt = jwtUtil.generateToken(newUserDetails.getUsername());
            ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();

            return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(message);
        }
        catch(IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    //login
    @PostMapping("login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody UserLoginRequest req) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        }
        catch(BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

        userService.handleLastLogin(req.getUsername());

        String message = "Login successful!";
        String jwt = jwtUtil.generateToken(req.getUsername());

        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(3600)
            .sameSite("Strict")
            .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(message);
    }

    @PostMapping("logout")
    @ResponseBody
    public ResponseEntity<String> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .sameSite("Strict")
            .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Logout successful!");
    }

    //edit account
}
