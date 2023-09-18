package com.lgm.demo.service.impl;

import com.lgm.demo.model.dto.request.SignUpRequest;
import com.lgm.demo.model.enumeration.ERole;
import com.lgm.demo.model.exceptions.EmailAlreadyExistsException;
import com.lgm.demo.model.exceptions.UsernameAlreadyExistsException;
import com.lgm.demo.security.Role;
import com.lgm.demo.model.User;
import com.lgm.demo.model.dto.request.SignInRequest;
import com.lgm.demo.model.dto.response.JwtResponse;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.WebSecurityConfig;
import com.lgm.demo.security.jwt.JwtUtils;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.ImageService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           JwtUtils jwtUtils,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public boolean usernameExists(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getLoggedInUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String) return null;
        return (User)principal;
    }

    public boolean isAdminOfCompetition(Long competitionId){
        User user = getLoggedInUser();
        for(Long id: user.getAdminOfCompetitions()){
            if(competitionId.equals(id)) return true;
        }
        return false;
    }

    @Override
    public JwtResponse signIn(SignInRequest signInRequest) throws IOException {
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
                user.getAdminOfCompetitions(),
                ImageService.getBase64Image(user.getProfileImagePath()));
        return jwtResponse;
    }

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        if (usernameExists(signUpRequest.getUsername()))
            throw new UsernameAlreadyExistsException(signUpRequest);

        if (emailExists(signUpRequest.getEmail()))
            throw new EmailAlreadyExistsException(signUpRequest);

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                WebSecurityConfig.getPasswordEncoder().encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                signUpRequest.getSex()
        );

        user.setProfileImagePath(ImageService.getRandomProfileImagePath(user));
        addRoleToUser(user, ERole.ROLE_USER);
    }

    private void addRoleToUser(User user, ERole role){
        Set<Role> roles = user.getRoles();
        roles.add(new Role(role));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
