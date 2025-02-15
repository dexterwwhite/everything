package com.usagi.everything.dto.response;

/**
 * DTO Response class
 * This class is used as output for user registration (/user/register)
 */
public class NewUserResponse {
    private String token;
    private String username;
    private Long userId;
    private String message;

    public NewUserResponse() {}

    public NewUserResponse(String name, Long id, String msg) {
        token = "";
        username = name;
        userId = id;
        message = msg;
    }

    public NewUserResponse(String msg) {
        token = "";
        username = "";
        userId = null;
        message = msg;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
