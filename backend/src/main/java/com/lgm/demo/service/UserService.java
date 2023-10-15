package com.lgm.demo.service;

import com.lgm.demo.model.dto.request.ChangeEmailRequest;
import com.lgm.demo.model.dto.request.ChangePasswordRequest;
import com.lgm.demo.model.dto.request.UpdateUserRequest;
import com.lgm.demo.model.dto.request.ChangeUsernameRequest;
import com.lgm.demo.model.dto.response.JwtResponse;
import com.lgm.demo.model.dto.response.UserResponse;

import java.io.IOException;

public interface UserService{
    UserResponse updateUser(UpdateUserRequest request) throws IOException;
    UserResponse getUser() throws IOException;
    JwtResponse updatePassword(ChangePasswordRequest request) throws IOException;
    JwtResponse updateUsername(ChangeUsernameRequest request) throws IOException;
    JwtResponse updateEmail(ChangeEmailRequest request) throws IOException;
}
