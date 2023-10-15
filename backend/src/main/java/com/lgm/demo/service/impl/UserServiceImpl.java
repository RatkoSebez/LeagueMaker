package com.lgm.demo.service.impl;

import com.lgm.demo.model.User;
import com.lgm.demo.model.dto.request.*;
import com.lgm.demo.model.dto.response.JwtResponse;
import com.lgm.demo.model.dto.response.UserResponse;
import com.lgm.demo.exception.EmailAlreadyExistsException;
import com.lgm.demo.exception.IncorrectPasswordException;
import com.lgm.demo.exception.UsernameAlreadyExistsException;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.WebSecurityConfig;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AuthService authService;

    public UserServiceImpl(UserRepository userRepository, AuthService authService){
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request) throws IOException{
        User user = authService.getLoggedInUser();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setBio(request.getBio());
        userRepository.save(user);
        return UserResponse.entityToDto(user);
    }

    @Override
    public UserResponse getUser() throws IOException{
        return UserResponse.entityToDto(authService.getLoggedInUser());
    }

    @Override
    public JwtResponse updatePassword(ChangePasswordRequest request) throws IOException{
        User user = authService.getLoggedInUser();
        if(!passwordsAreEqual(user.getPassword(), request.getCurrentPassword()))
            throw new IncorrectPasswordException(request);
        PasswordEncoder encoder = WebSecurityConfig.getPasswordEncoder();
        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user);
        // sign in again to refresh jwt token data (password has changed)
        return authService.signIn(new SignInRequest(user.getUsername(), request.getNewPassword()));
    }

    public JwtResponse updateUsername(ChangeUsernameRequest request) throws IOException{
        if(authService.usernameExists(request.getNewUsername()))
            throw new UsernameAlreadyExistsException(request);
        User user = authService.getLoggedInUser();
        if(!passwordsAreEqual(user.getPassword(), request.getPassword()))
            throw new IncorrectPasswordException(request);
        user.setUsername(request.getNewUsername());
        userRepository.save(user);
        // sign in again to refresh jwt token data (username has changed)
        return authService.signIn(new SignInRequest(user.getUsername(), request.getPassword()));
    }

    @Override
    public JwtResponse updateEmail(ChangeEmailRequest request) throws IOException{
        System.out.println(request.getNewEmail() + " " + request.getPassword());
        if(authService.emailExists(request.getNewEmail()))
            throw new EmailAlreadyExistsException(request);
        User user = authService.getLoggedInUser();
        if(!passwordsAreEqual(user.getPassword(), request.getPassword()))
            throw new IncorrectPasswordException(request);
        user.setEmail(request.getNewEmail());
        userRepository.save(user);
        // sign in again to refresh jwt token data (email has changed)
        return authService.signIn(new SignInRequest(user.getUsername(), request.getPassword()));
    }

    private Boolean passwordsAreEqual(String encodedPassword, String rawPassword){
        PasswordEncoder encoder = WebSecurityConfig.getPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }
}
