package com.lgm.demo.controller;

import com.lgm.demo.model.dto.request.ChangeEmailRequest;
import com.lgm.demo.model.dto.request.ChangePasswordRequest;
import com.lgm.demo.model.dto.request.UpdateUserRequest;
import com.lgm.demo.model.dto.request.ChangeUsernameRequest;
import com.lgm.demo.model.dto.response.JwtResponse;
import com.lgm.demo.model.dto.response.UserResponse;
import com.lgm.demo.service.ImageService;
import com.lgm.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUser() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser());
    }

    @PostMapping
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UpdateUserRequest userRequest) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userRequest));
    }

    @PostMapping("/password")
    public ResponseEntity<JwtResponse> changePassword(@Valid @RequestBody ChangePasswordRequest setPasswordRequest) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePassword(setPasswordRequest));
    }

    @PostMapping("/username")
    public ResponseEntity<JwtResponse> changeUsername(@Valid @RequestBody ChangeUsernameRequest setUsernameRequest) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUsername(setUsernameRequest));
    }

    @PostMapping("/email")
    public ResponseEntity<JwtResponse> updateEmail(@Valid @RequestBody ChangeEmailRequest setEmailRequest) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateEmail(setEmailRequest));
    }
}
