package com.usagi.everything.service;

import java.time.LocalDate;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usagi.everything.dto.request.NewUserRequest;
import com.usagi.everything.dto.response.NewUserResponse;
import com.usagi.everything.model.User;
import com.usagi.everything.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public NewUserResponse registerUser(NewUserRequest nur) {
        if(userRepository.existsByUsername(nur.getUsername())) {
            throw new IllegalArgumentException("ERROR: User with this username already exists!");
        }
        else if(userRepository.existsByEmail(nur.getEmail())) {
            throw new IllegalArgumentException("ERROR: User with this email already exists!");
        }
        else if(!isStrongPassword(nur.getPassword()))
        {
            throw new IllegalArgumentException("ERROR: Password is not strong enough!");
        }

        String hashedPassword = passwordEncoder.encode(nur.getPassword());
        User dbUser = userRepository.save(new User(nur.getUsername(), hashedPassword, nur.getFirstName(), nur.getLastName(), nur.getEmail()));
        
        String message = "User " + dbUser.getUsername() + " has been registered successfully!";

        return new NewUserResponse(dbUser.getUsername(), dbUser.getUserId(), message);
    }

    public void handleLastLogin(String username) {
        User user = userRepository.findByUsername(username);

        LocalDate lastLogin = user.getLastLogin().toLocalDate();

        // Increment freak level if this is their first login today
        if(!lastLogin.equals(LocalDate.now())) {
            user.incrementFreakLevel(1);
        }

        // Check if they have a login streak, otherwise reset streak to 1
        if(lastLogin.equals(LocalDate.now().minusDays(1))) {
            user.setLoginStreak(user.getLoginStreak() + 1);
        }
        else if(!lastLogin.equals(LocalDate.now())) {
            user.setLoginStreak(1);
        }

        // Bonus freak level for 7 logins in a row
        if(user.getLoginStreak() > 0 && (user.getLoginStreak() % 7 == 0)) {
            user.incrementFreakLevel(10);
        }

        // Sets last login to now
        user.setLastLogin();

        userRepository.save(user);
    }

    private boolean isStrongPassword(String password) {
        String [] regexPatterns = {".*[A-Z].*", ".*[a-z].*", ".*\\d.*", ".*[^a-zA-Z0-9].*"};
        if(password.length() < 8) {
            return false;
        }
        
        for(String pattern : regexPatterns) {
            if(!password.matches(pattern)) {
                return false;
            }
        }

        return true;
    }
}
