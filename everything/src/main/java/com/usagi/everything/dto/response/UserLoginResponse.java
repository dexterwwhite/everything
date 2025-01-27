package com.usagi.everything.dto.response;

public class UserLoginResponse {
    private boolean success;
    private String token;
    private String message;

    public UserLoginResponse() {}

    public UserLoginResponse(boolean success, String token, String message) {
        this.success = success;
        this.token = token;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
