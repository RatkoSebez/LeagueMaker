package com.lgm.demo.service;

import com.lgm.demo.model.User;
import com.lgm.demo.model.dto.request.ChangeEmailRequest;
import com.lgm.demo.model.dto.request.ChangePasswordRequest;
import com.lgm.demo.model.dto.request.UpdateUserRequest;
import com.lgm.demo.model.dto.request.ChangeUsernameRequest;
import com.lgm.demo.model.dto.response.JwtResponse;
import com.lgm.demo.model.dto.response.UserResponse;
import com.lgm.demo.exception.EmailAlreadyExistsException;
import com.lgm.demo.exception.IncorrectPasswordException;
import com.lgm.demo.exception.UsernameAlreadyExistsException;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void UpdateUser_FieldsAreUpdated() throws IOException {
        User user = User.builder().username("username").surname("surname").bio("bio").build();
        UpdateUserRequest request = new UpdateUserRequest("newName", "newSurname", "new bio");
        when(authService.getLoggedInUser()).thenReturn(user);

        userService.updateUser(request);

        Assertions.assertThat(user.getName()).isEqualTo(request.getName());
        Assertions.assertThat(user.getSurname()).isEqualTo(request.getSurname());
        Assertions.assertThat(user.getBio()).isEqualTo(request.getBio());
    }

    @Test
    public void GetUser_UserIsReturned() throws IOException {
        User user = User.builder().username("username").surname("surname").bio("bio").build();
        when(authService.getLoggedInUser()).thenReturn(user);

        UserResponse returnedUser = userService.getUser();

        Assertions.assertThat(returnedUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdatePassword_ThrowsExceptionWhenPasswordIsIncorrect() throws IOException {
        // hashed password is 123456789
        User user = User.builder().username("username").surname("surname").bio("bio").password("$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq").build();
        ChangePasswordRequest request = new ChangePasswordRequest("wrongPassword", "newpassword");
        when(authService.getLoggedInUser()).thenReturn(user);
        when(authService.signIn(Mockito.any())).thenReturn(new JwtResponse());

        assertThrows(IncorrectPasswordException.class, () -> userService.updatePassword(request));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdatePassword_ReturnsJwtResponseIfPasswordIsCorrect() throws IOException {
        // hashed password is 123456789
        User user = User.builder().username("username").surname("surname").bio("bio").password("$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq").build();
        ChangePasswordRequest request = new ChangePasswordRequest("123456789", "newpassword");
        when(authService.getLoggedInUser()).thenReturn(user);
        when(authService.signIn(Mockito.any())).thenReturn(new JwtResponse());

        JwtResponse response = userService.updatePassword(request);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void UpdatePassword_PasswordIsUpdated() throws IOException {
        // hashed password is 123456789
        String oldPassword = "$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq";
        User user = User.builder().username("username").surname("surname").bio("bio").password(oldPassword).build();
        ChangePasswordRequest request = new ChangePasswordRequest("123456789", "newpassword");
        when(authService.getLoggedInUser()).thenReturn(user);

        userService.updatePassword(request);

        Assertions.assertThat(user.getPassword()).isNotEqualTo(oldPassword);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdateUsername_ThrowsExceptionWhenNewUsernameExists() throws IOException {
        ChangeUsernameRequest request = new ChangeUsernameRequest("newUsername", "123456789");
        when(authService.usernameExists(request.getNewUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.updateUsername(request));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdateUsername_ThrowsExceptionWhenPasswordIsIncorrect() throws IOException {
        // hashed password is 123456789
        User user = User.builder().username("username").surname("surname").bio("bio").password("$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq").build();
        ChangeUsernameRequest request = new ChangeUsernameRequest("newUsername", "12345678");
        when(authService.usernameExists(request.getNewUsername())).thenReturn(false);
        when(authService.getLoggedInUser()).thenReturn(user);
        when(authService.signIn(Mockito.any())).thenReturn(new JwtResponse());

        assertThrows(IncorrectPasswordException.class, () -> userService.updateUsername(request));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdateUsername_UsernameIsUpdated() throws IOException {
        // hashed password is 123456789
        User user = User.builder().username("username").surname("surname").bio("bio").password("$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq").build();
        ChangeUsernameRequest request = new ChangeUsernameRequest("newUsername", "123456789");
        when(authService.usernameExists(request.getNewUsername())).thenReturn(false);
        when(authService.getLoggedInUser()).thenReturn(user);
        when(authService.signIn(Mockito.any())).thenReturn(new JwtResponse());

        userService.updateUsername(request);

        Assertions.assertThat(user.getUsername()).isEqualTo(request.getNewUsername());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdateUsername_JwtResponseIsNotNull() throws IOException {
        // hashed password is 123456789
        User user = User.builder().username("username").surname("surname").bio("bio").password("$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq").build();
        ChangeUsernameRequest request = new ChangeUsernameRequest("newUsername", "123456789");
        when(authService.usernameExists(request.getNewUsername())).thenReturn(false);
        when(authService.getLoggedInUser()).thenReturn(user);
        when(authService.signIn(Mockito.any())).thenReturn(new JwtResponse());

        JwtResponse response = userService.updateUsername(request);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdateEmail_ThrowsExceptionWhenNewEmailExists() throws IOException {
        ChangeEmailRequest request = new ChangeEmailRequest("123456789", "newemail@gmail.com");
        when(authService.emailExists(request.getNewEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.updateEmail(request));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdateEmail_ThrowsExceptionWhenPasswordIsIncorrect() throws IOException {
        // hashed password is 123456789
        User user = User.builder().username("username").surname("surname").bio("bio").password("$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq").build();
        ChangeEmailRequest request = new ChangeEmailRequest("wrongpass", "newemail@gmail.com");
        when(authService.emailExists(request.getNewEmail())).thenReturn(false);
        when(authService.getLoggedInUser()).thenReturn(user);
        when(authService.signIn(Mockito.any())).thenReturn(new JwtResponse());

        assertThrows(IncorrectPasswordException.class, () -> userService.updateEmail(request));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdateEmail_EmailIsUpdated() throws IOException {
        // hashed password is 123456789
        User user = User.builder().username("username").surname("surname").email("oldemail@gmail.com").bio("bio").password("$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq").build();
        ChangeEmailRequest request = new ChangeEmailRequest("123456789", "newemail@gmail.com");
        when(authService.usernameExists(request.getNewEmail())).thenReturn(false);
        when(authService.getLoggedInUser()).thenReturn(user);
        when(authService.signIn(Mockito.any())).thenReturn(new JwtResponse());

        userService.updateEmail(request);

        Assertions.assertThat(user.getEmail()).isEqualTo(request.getNewEmail());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void UpdateEmail_JwtResponseIsNotNull() throws IOException {
        // hashed password is 123456789
        User user = User.builder().username("username").surname("surname").bio("bio").password("$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq").build();
        ChangeEmailRequest request = new ChangeEmailRequest("123456789", "newemail@gmail.com");
        when(authService.usernameExists(request.getNewEmail())).thenReturn(false);
        when(authService.getLoggedInUser()).thenReturn(user);
        when(authService.signIn(Mockito.any())).thenReturn(new JwtResponse());

        JwtResponse response = userService.updateEmail(request);

        Assertions.assertThat(response).isNotNull();
    }
}
