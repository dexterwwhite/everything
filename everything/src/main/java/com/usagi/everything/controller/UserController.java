package com.usagi.everything.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.usagi.everything.dto.request.NewUserRequest;
import com.usagi.everything.dto.response.NewUserResponse;
import com.usagi.everything.service.UserService;

@RestController
@RequestMapping("user/")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //register
    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<NewUserResponse> register(@RequestBody NewUserRequest newUserDetails) {
        try {
            NewUserResponse addedUser = userService.registerUser(newUserDetails);
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
    public String authenticate(@RequestParam String father) {
        return father + " oh hey there@";
    }

    //

    //edit account

    @GetMapping("getUserByUsername")
    @ResponseBody
    public UserDetails getUserByUsername(@RequestParam String username) {
        return userService.loadUserByUsername(username);
    }
}
