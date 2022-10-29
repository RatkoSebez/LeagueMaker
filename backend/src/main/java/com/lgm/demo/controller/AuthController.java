package com.lgm.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.lgm.demo.model.ERole;
import com.lgm.demo.model.payload.request.SignInRequest;
import com.lgm.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgm.demo.model.User;
import com.lgm.demo.model.payload.request.SignUpRequest;
import com.lgm.demo.model.payload.response.JwtResponse;
import com.lgm.demo.model.payload.response.MessageResponse;
import com.lgm.demo.security.jwt.JwtUtils;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder encoder;
    private JwtUtils jwtUtils;
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = (User) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles,
                user.getAdminOfCompetitions());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (authService.usernameExists(signUpRequest.getUsername()))
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));

        if (authService.emailExists(signUpRequest.getEmail()))
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getSurname()
        );

        authService.addRoleToUser(user, ERole.ROLE_USER);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
