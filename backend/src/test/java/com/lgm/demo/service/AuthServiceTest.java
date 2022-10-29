package com.lgm.demo.service;

import com.lgm.demo.model.ERole;
import com.lgm.demo.model.Role;
import com.lgm.demo.model.User;
import com.lgm.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock private UserRepository userRepository;
    private AuthService underTest;

    @Test
    void doesUsernameExists() {
        // arrange
        String username = "user";
        // act
        underTest.usernameExists(username);
        // assert
        verify(userRepository).existsByUsername(username);
    }

    @Test
    void emailExists() {
        // arrange
        String email = "email";
        // act
        underTest.emailExists(email);
        // assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsByEmail(argumentCaptor.capture());
        String capturedEmail = argumentCaptor.getValue();
        assertThat(capturedEmail).isEqualTo(email);
    }

    @Test
    void setRoleForUser() {
        // arrange
        User user = new User();
        ERole role = ERole.ROLE_USER;
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(role));
        //act
        underTest.addRoleToUser(user, role);
        // assert
        verify(userRepository).save(user);
    }
}