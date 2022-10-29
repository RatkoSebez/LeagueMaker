package com.lgm.demo.controller;

import com.lgm.demo.repository.RoleRepository;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.jwt.AuthEntryPointJwt;
import com.lgm.demo.security.jwt.JwtUtils;
import com.lgm.demo.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TestController.class)
class TestControllerTest {
    // idk why I need this mocks but tests would not compile without them
    @MockBean private UserDetailsServiceImpl userDetailsService;
    @MockBean private AuthEntryPointJwt authEntryPointJwt;
    @MockBean private JwtUtils jwtUtils;
    @MockBean private RoleRepository roleRepository;
    @MockBean private UserRepository userRepository;
    // idk why I need this mocks but tests would not compile without them

    @Autowired
    private MockMvc mvc;

    @Test
    void allAccessReturnsMessage() throws Exception {
        // arrange
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/test/all");
        // act
        MvcResult result = mvc.perform(request).andReturn();
        // assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Public Content.");
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void onlyAdminCanAccess() throws Exception {
        int status = UtilForEndpointProtection.statusCodeForGetRequest("/api/v1/test/admin", mvc, "USER");
        assertThat(status).isEqualTo(403);
    }

    @Test
    void userAndAdminCanAccess() throws Exception {
        int statusAdmin = UtilForEndpointProtection.statusCodeForGetRequest("/api/v1/test/user", mvc, "ADMIN");
        int statusUser = UtilForEndpointProtection.statusCodeForGetRequest("/api/v1/test/user", mvc, "USER");
        assertThat(statusAdmin).isEqualTo(200);
        assertThat(statusUser).isEqualTo(200);
    }
}