package com.lgm.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgm.demo.model.dto.response.JwtResponse;
import com.lgm.demo.model.enumeration.ESex;
import com.lgm.demo.model.dto.request.SignInRequest;
import com.lgm.demo.model.dto.request.SignUpRequest;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.repository.RoleRepository;
import com.lgm.demo.repository.ScheduleRepository;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.jwt.AuthEntryPointJwt;
import com.lgm.demo.security.jwt.JwtUtils;
import com.lgm.demo.security.services.UserDetailsServiceImpl;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.ImageService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
//@AutoConfigureMockMvc(addFilters = false) // disable spring security filters
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private final String path = "/api/v1/auth";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean private AuthService authService;

    // idk why I need this mocks but tests would not compile without them
    @MockBean private UserDetailsServiceImpl userDetailsService;
    @MockBean private AuthEntryPointJwt authEntryPointJwt;
    @MockBean private JwtUtils jwtUtils;
    @MockBean private RoleRepository roleRepository;
    @MockBean private UserRepository userRepository;
    @MockBean private CompetitorRepository competitorRepository;
    @MockBean private ScheduleRepository scheduleRepository;
    @MockBean private ImageService imageService;

    private static final List<SignUpRequest> invalidSignUpRequests = new ArrayList<>();
    private static final List<SignUpRequest> validSignUpRequests = new ArrayList<>();

    @BeforeAll
    public static void init(){
        validSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "testName", "testSurname", "123456asdAAF!", ESex.MALE));
        // invalid username
        invalidSignUpRequests.add(new SignUpRequest("te", "test@gmail.com", "testName", "testSurname", "123456asdAAF!", ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("tefdsdfgedsdfedsdefds", "test@gmail.com", "testName", "testSurname", "123456asdAAF!", ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("teadsf*", "test@gmail.com", "testName", "testSurname", "123456asdAAF!", ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("tass$e", "test@gmail.com", "testName", "testSurname", "123456asdAAF!", ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("t-edff", "test@gmail.com", "testName", "testSurname", "123456asdAAF!", ESex.MALE));
        // invalid email
        invalidSignUpRequests.add(new SignUpRequest("test1", "test", "testName", "testSurname", "123456asdAAF!", ESex.MALE));
        // invalid name
        invalidSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "asdfasdfasdfasdfasdfasdfasdfasdf", "testSurname", "123456asdAAF!", ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "testName!", "testSurname", "123456asdAAF!", ESex.MALE));
        // invalid surname
        invalidSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "testName", "asdfasdfasdfasdfasdfasdfasdfasdf", "123456asdAAF!", ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "testName", "testSurname!", "123456asdAAF!", ESex.MALE));
        // invalid password
        invalidSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "testName", "testSurname", null, ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "testName", "testSurname", "1234567", ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "testName", "testSurname", "012345678901234567890123456789012345678901234567891", ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "testName", "testSurname", "123456asdAAF?", ESex.MALE));
        invalidSignUpRequests.add(new SignUpRequest("test1", "test@gmail.com", "testName", "testSurname", "123456as_dAAF", ESex.MALE));
    }

    @Test
    void SignIn_ShouldReturnOkAfterSuccessfulLogin() throws Exception {
        JwtResponse response = JwtResponse.builder().email("test@gmail.com").username("testUsername").build();
        when(authService.signIn(ArgumentMatchers.any())).thenReturn(response);

        SignInRequest request = new SignInRequest("user", "123456789");
        mvc.perform(post(path + "/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", CoreMatchers.is(response.getUsername())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(response.getEmail())));
                // .andDo(MockMvcResultHandlers.print()); // prints http response
    }

//    @Test
//    void signInWithWrongCredentials() throws Exception {
//        // I can not test this unless I use database, so this unit test is useless
//        JwtResponse jwtResponse = JwtResponse.builder().username("user").build();
//        given(authService.signIn(ArgumentMatchers.any())).willReturn(jwtResponse);
//
//        SignInRequest invalidSignInRequest = new SignInRequest("nonExistingUsername", "notpass");
//        mvc.perform(post("/api/v1/auth/signin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(invalidSignInRequest)))
//                .andExpect(status().isUnauthorized());
//    }

    @Test
    void SignUp_Return400WhenRequestsAreInvalid() throws Exception {
        doNothing().when(authService).signUp(ArgumentMatchers.any());

        for(SignUpRequest request : invalidSignUpRequests){
            mvc.perform(post(path + "/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    void SignUp_Return200WhenRequestsAreValid() throws Exception {
        doNothing().when(authService).signUp(ArgumentMatchers.any());

        for(SignUpRequest request : validSignUpRequests){
            mvc.perform(post(path + "/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }
    }
}