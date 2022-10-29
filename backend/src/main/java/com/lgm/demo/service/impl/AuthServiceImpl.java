package com.lgm.demo.service.impl;

import com.lgm.demo.model.ERole;
import com.lgm.demo.model.Role;
import com.lgm.demo.model.User;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;

    public boolean usernameExists(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public void addRoleToUser(User user, ERole role){
        Set<Role> roles = user.getRoles();
        roles.add(new Role(role));
        user.setRoles(roles);
        userRepository.save(user);
    }

    public User getLoggedInUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String) return null;
        return (User)principal;
    }
}
