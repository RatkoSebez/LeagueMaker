package com.lgm.demo.service;

import com.lgm.demo.model.dto.request.SignUpRequest;
import com.lgm.demo.model.enumeration.ERole;
import com.lgm.demo.model.User;
import com.lgm.demo.model.dto.request.SignInRequest;
import com.lgm.demo.model.dto.response.JwtResponse;

import java.io.IOException;

public interface AuthService {
    boolean usernameExists(String username);
    boolean emailExists(String email);
    User getLoggedInUser();
    boolean isAdminOfCompetition(Long competitionId);
    JwtResponse signIn(SignInRequest signInRequest) throws IOException;
    void signUp(SignUpRequest signUpRequest);
}
