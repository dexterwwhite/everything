package com.usagi.everything.dto.request;

public class NewUserRequest {
    
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    public NewUserRequest() {}

    public NewUserRequest(String u, String p, String f, String l, String e) {
        username = u;
        password = p;
        firstName = f;
        lastName = l;
        email = e;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
