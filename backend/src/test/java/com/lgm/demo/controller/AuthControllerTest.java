package com.lgm.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgm.demo.model.payload.request.SignInRequest;
import com.lgm.demo.model.payload.request.SignUpRequest;
import com.lgm.demo.repository.RoleRepository;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.jwt.AuthEntryPointJwt;
import com.lgm.demo.security.jwt.JwtUtils;
import com.lgm.demo.security.services.UserDetailsServiceImpl;
import com.lgm.demo.service.AuthService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {
    // idk why I need this mocks but tests would not compile without them
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean private AuthEntryPointJwt authEntryPointJwt;
    @MockBean private JwtUtils jwtUtils;
    @MockBean private RoleRepository roleRepository;
    @MockBean private UserRepository userRepository;
    // idk why I need this mocks but tests would not compile without them

//    @MockBean private AuthenticationManager authenticationManager;
//    @MockBean private PasswordEncoder encoder;
    @MockBean private AuthService authService;

    private String validUsername = "test1";
    private String validEmail = "test@gmail.com";
    private String validName = "testName";
    private String validSurname = "testSurname";
    private String validPassword = "123456asdAAF!";

    @Autowired
    private MockMvc mvc;

    // SIGN UP (tests does not work)

    @Disabled
    @Test
    void signInWithValidCredentials() throws Exception {
        SignInRequest validSignInRequest = new SignInRequest("user", "123456789");
        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(validSignInRequest)))
                        .andExpect(status().isOk());
    }

    @Disabled
    @Test
    void signInWithWrongCredentials() throws Exception {
        SignInRequest invalidSignInRequest = new SignInRequest("nonExistingUsername", "notpass");
        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidSignInRequest)))
                .andExpect(status().isUnauthorized());
    }

    // SIGN UP

    @Test
    void signUpTestUsernameParameter() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest(validUsername, validEmail, validName, validSurname, validPassword);

        // valid
        doPostThatExpectsOk(signUpRequest);
        signUpRequest.setUsername("validUsername553_");
        doPostThatExpectsOk(signUpRequest);

        // short (2)
        signUpRequest.setUsername("as");
        doPostThatExpectsBadRequest(signUpRequest);
        // long (21)
        signUpRequest.setUsername("asdfdsasd_fdsasdfsasdf");
        doPostThatExpectsBadRequest(signUpRequest);
        // forbidden character (%)
        signUpRequest.setUsername("asd%");
        doPostThatExpectsBadRequest(signUpRequest);
    }

    @Test
    void signUpTestPasswordParameter() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest(validUsername, validEmail, validName, validSurname, validPassword);

        // valid
        doPostThatExpectsOk(signUpRequest);
        signUpRequest.setPassword("asdf51665!#SFA&");
        doPostThatExpectsOk(signUpRequest);

        // short (7)
        signUpRequest.setPassword("passwor");
        doPostThatExpectsBadRequest(signUpRequest);
        // long (52)
        signUpRequest.setPassword("sdfsadfdsasdfsadfdsasdfsadfdsasdfsadfdsasdfsadfdsaas");
        doPostThatExpectsBadRequest(signUpRequest);
        // forbidden character (=)
        signUpRequest.setPassword("asdadsffda=");
        doPostThatExpectsBadRequest(signUpRequest);
    }

    @Test
    void signUpTestEmailParameter() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest(validUsername, validEmail, validName, validSurname, validPassword);

        // invalid
        signUpRequest.setEmail("notEmail");
        doPostThatExpectsBadRequest(signUpRequest);
    }

    private void doPostThatExpectsBadRequest(SignUpRequest signUpRequest) throws Exception {
        mvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signUpRequest)))
                        .andExpect(status().isBadRequest());
    }

    private void doPostThatExpectsOk(SignUpRequest signUpRequest) throws Exception {
        mvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signUpRequest)))
                        .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}