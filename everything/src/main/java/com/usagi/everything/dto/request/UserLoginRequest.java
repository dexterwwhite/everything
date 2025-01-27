package com.usagi.everything.dto.request;

public class UserLoginRequest {
    private String username;
    private String password;

    public UserLoginRequest() {}

    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
