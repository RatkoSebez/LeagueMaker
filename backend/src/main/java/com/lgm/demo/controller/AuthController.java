package com.lgm.demo.controller;

import java.io.IOException;

import javax.validation.Valid;

import com.lgm.demo.model.dto.request.SignInRequest;
import com.lgm.demo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lgm.demo.model.dto.request.SignUpRequest;
import com.lgm.demo.model.dto.response.JwtResponse;
import com.lgm.demo.model.dto.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signIn(signInRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User registered successfully!"));
    }
}
