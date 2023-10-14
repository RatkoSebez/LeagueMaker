package com.lgm.demo.service;

import com.lgm.demo.model.dto.request.SignUpRequest;
import com.lgm.demo.model.enumeration.ERole;
import com.lgm.demo.model.enumeration.ESex;
import com.lgm.demo.exception.EmailAlreadyExistsException;
import com.lgm.demo.exception.UsernameAlreadyExistsException;
import com.lgm.demo.security.Role;
import com.lgm.demo.model.User;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.jwt.JwtUtils;
import com.lgm.demo.service.impl.AuthServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private JwtUtils jwtUtils;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private ImageService imageService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void UsernameExists_ReturnsTrueIfExistsAndFalseIfDont(){
        String existingUsername = "user";
        String nonexistentUsername = "notuser";
        when(userRepository.existsByUsername(existingUsername)).thenReturn(true);
        when(userRepository.existsByUsername(nonexistentUsername)).thenReturn(false);

        Boolean shouldBeTrue = authService.usernameExists(existingUsername);
        Boolean shouldBeFalse = authService.usernameExists(nonexistentUsername);

        Assertions.assertThat(shouldBeTrue).isTrue();
        Assertions.assertThat(shouldBeFalse).isFalse();
    }

    @Test
    void EmailExists_ReturnsTrueIfExistsAndFalseIfDont(){
        String existingEmail = "email";
        String nonexistentEmail = "notemail";
        when(userRepository.existsByEmail(existingEmail)).thenReturn(true);
        when(userRepository.existsByEmail(nonexistentEmail)).thenReturn(false);

        Boolean shouldBeTrue = authService.emailExists(existingEmail);
        Boolean shouldBeFalse = authService.emailExists(nonexistentEmail);

        Assertions.assertThat(shouldBeTrue).isTrue();
        Assertions.assertThat(shouldBeFalse).isFalse();
    }

    @Disabled
    @Test
    void AddRoleToUser_RoleIsAdded() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        User user = User.builder().roles(roles).build();

//        authService.addRoleToUser(user, ERole.ROLE_ADMIN);

        Assertions.assertThat(user.getRoles().size()).isEqualTo(2);
    }


    @Test
    void GetLoggedInUser_() throws IOException {
        // idk how to test this function
    }

    @Test
    void SignIn_() throws IOException {
        // idk how to test this function
    }

    @Test
    void SignUp_Return409WhenUsernameExists(){
        SignUpRequest request = new SignUpRequest("asdf", "test@gmail.com", "adsff", "gdsad", "123456789", ESex.MALE);
        when(authService.usernameExists(request.getUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> authService.signUp(request));
    }

    @Test
    void SignUp_Return409WhenEmailExists() throws Exception {
        SignUpRequest request = new SignUpRequest("asdf", "test@gmail.com", "adsff", "gdsad", "123456789", ESex.MALE);
        when(authService.emailExists(request.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> authService.signUp(request));
    }
}