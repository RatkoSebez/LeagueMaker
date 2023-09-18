package com.lgm.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgm.demo.model.constant.ExceptionMessageConstant;
import com.lgm.demo.model.dto.request.ChangeEmailRequest;
import com.lgm.demo.model.dto.request.ChangePasswordRequest;
import com.lgm.demo.model.dto.request.UpdateUserRequest;
import com.lgm.demo.model.dto.request.ChangeUsernameRequest;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.repository.RoleRepository;
import com.lgm.demo.repository.ScheduleRepository;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.jwt.AuthEntryPointJwt;
import com.lgm.demo.security.jwt.JwtUtils;
import com.lgm.demo.security.services.UserDetailsServiceImpl;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.ImageService;
import com.lgm.demo.service.impl.UserServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private final String path = "/api/v1/user";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    // idk why I need this mocks but tests would not compile without them
    @MockBean
    private AuthService authService;
    @MockBean private UserDetailsServiceImpl userDetailsService;
    @MockBean private AuthEntryPointJwt authEntryPointJwt;
    @MockBean private JwtUtils jwtUtils;
    @MockBean private RoleRepository roleRepository;
    @MockBean private UserRepository userRepository;
    @MockBean private CompetitorRepository competitorRepository;
    @MockBean private ScheduleRepository scheduleRepository;
    @MockBean private ImageService imageService;

    @Test
    public void GetUser_ReturnsOk() throws Exception {
        mvc.perform(get(path)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdateUser_ReturnsOkWhenRequestIsValid() throws Exception {
        List<UpdateUserRequest> validRequests = new ArrayList<>();
        validRequests.add(new UpdateUserRequest("name", "surname", "my bio"));
        validRequests.add(new UpdateUserRequest("", "surname", "my bio"));
        validRequests.add(new UpdateUserRequest(null, "surname", "my bio"));
        validRequests.add(new UpdateUserRequest("name", "", "my bio"));
        validRequests.add(new UpdateUserRequest("name", null, null));

        for(UpdateUserRequest request : validRequests){
            mvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void UpdateUser_Returns404WhenRequestIsInvalid() throws Exception {
        List<UpdateUserRequest> invalidRequests = new ArrayList<>();
        invalidRequests.add(new UpdateUserRequest("namefdsadfnamefdsadfnamefdsad31", "surname", "my bio"));
        invalidRequests.add(new UpdateUserRequest("name", "namefdsadfnamefdsadfnamefdsad31", "my bio"));
        invalidRequests.add(new UpdateUserRequest("name", "name", "myasdffdsamyasdffdsamyasdffdsamya" +
                "sdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyas" +
                "dffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyas" +
                "dffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyas" +
                "dffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasd" +
                "ffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffdsamyasdffds501"));

        for(UpdateUserRequest request : invalidRequests){
            mvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", CoreMatchers.is(ExceptionMessageConstant.METHOD_ARGUMENT_NOT_VALID_MESSAGE)));
        }
    }

    @Test
    public void UpdatePassword_ReturnsOkWhenRequestIsValid() throws Exception {
        ChangePasswordRequest validRequest = new ChangePasswordRequest("password", "newpassword");

        mvc.perform(post(path + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdatePassword_Returns404WhenRequestIsInvalid() throws Exception {
        List<ChangePasswordRequest> invalidRequests = new ArrayList<>();
        invalidRequests.add(new ChangePasswordRequest(null, "newpassword"));
        invalidRequests.add(new ChangePasswordRequest("1234567", "newpassword"));
        invalidRequests.add(new ChangePasswordRequest("password", "newpasswornewpasswornewpasswornewpasswornewpasswo51"));
        invalidRequests.add(new ChangePasswordRequest("password", "newpass word"));
        invalidRequests.add(new ChangePasswordRequest("password", "newpassword<()"));

        for(ChangePasswordRequest request : invalidRequests){
            mvc.perform(post(path + "/password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", CoreMatchers.is(ExceptionMessageConstant.METHOD_ARGUMENT_NOT_VALID_MESSAGE)));
        }
    }

    @Test
    public void UpdateUsername_ReturnsOkWhenRequestIsValid() throws Exception {
        ChangeUsernameRequest validRequest = new ChangeUsernameRequest("adsffAS56_", "123456789");

        mvc.perform(post(path + "/username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdateUsername_Returns404WhenRequestIsInvalid() throws Exception {
        List<ChangeUsernameRequest> invalidRequests = new ArrayList<>();
        invalidRequests.add(new ChangeUsernameRequest(null, "123456789"));
        invalidRequests.add(new ChangeUsernameRequest("as", "123456789"));
        invalidRequests.add(new ChangeUsernameRequest("asdfasdfasdfasdfasdfa", "123456789"));
        invalidRequests.add(new ChangeUsernameRequest("user3*", "123456789"));
        invalidRequests.add(new ChangeUsernameRequest("user name", "123456789"));

        for(ChangeUsernameRequest request : invalidRequests){
            mvc.perform(post(path + "/username")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", CoreMatchers.is(ExceptionMessageConstant.METHOD_ARGUMENT_NOT_VALID_MESSAGE)));
        }
    }

    @Test
    public void UpdateEmail_ReturnsOkWhenRequestIsValid() throws Exception {
        ChangeEmailRequest validRequest = new ChangeEmailRequest("adsffAS56", "test@gmail.com");

        mvc.perform(post(path + "/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdateEmail_Returns404WhenRequestIsInvalid() throws Exception {
        List<ChangeEmailRequest> invalidRequests = new ArrayList<>();
        invalidRequests.add(new ChangeEmailRequest("adsffAS56", "test@"));
        invalidRequests.add(new ChangeEmailRequest("adsffAS56", "@gmail.com"));
        invalidRequests.add(new ChangeEmailRequest("adsffAS56", "testgmail.com"));
        invalidRequests.add(new ChangeEmailRequest("adsffAS56-?", "test@gmail.com"));

        for(ChangeEmailRequest request : invalidRequests){
            mvc.perform(post(path + "/email")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", CoreMatchers.is(ExceptionMessageConstant.METHOD_ARGUMENT_NOT_VALID_MESSAGE)));
        }
    }
}
