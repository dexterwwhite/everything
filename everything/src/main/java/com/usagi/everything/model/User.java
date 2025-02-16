package com.usagi.everything.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private int freakLevel;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private int loginStreak;

    public User() {}

    public User(String uname, String pass, String fname, String lname, String email) {
        username = uname;
        password = pass;
        firstName = fname;
        lastName = lname;
        this.email = email;
        freakLevel = 0;
        role = "USER";
        lastLogin = LocalDateTime.now();
        loginStreak = 1;
    }

    /**
     * Getter Methods
     */

    public long getUserId() {
        return userId;
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

    public int getFreakLevel() {
        return freakLevel;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public int getLoginStreak() {
        return loginStreak;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }

    /**
     * Setter/Update Methods
     */
    public void setFreakLevel(int newLevel) {
        freakLevel = newLevel;
    }

    public void incrementFreakLevel(int addedLevel) {
        freakLevel += addedLevel;
    }

    public void setLastLogin() {
        lastLogin = LocalDateTime.now();
    }

    public void setLoginStreak(int streak) {
        loginStreak = streak;
    }
}