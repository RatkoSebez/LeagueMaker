package com.lgm.demo.service;

import com.lgm.demo.model.ERole;
import com.lgm.demo.model.User;

public interface AuthService {
    boolean usernameExists(String username);
    boolean emailExists(String email);
    void addRoleToUser(User user, ERole role);
    User getLoggedInUser();
}
