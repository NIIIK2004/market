package com.example.news.dao;

import com.example.news.model.Role;
import com.example.news.model.User;

import java.util.Optional;

public interface UserDao {

    User save(User user, Role role);
    void delete(Long id);
    Optional<User> findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
}
