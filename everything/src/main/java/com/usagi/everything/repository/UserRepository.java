package com.usagi.everything.repository;

import org.springframework.data.repository.CrudRepository;

import com.usagi.everything.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    public User findByUsername(String username);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
