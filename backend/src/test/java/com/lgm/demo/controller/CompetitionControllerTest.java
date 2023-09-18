package com.lgm.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.repository.RoleRepository;
import com.lgm.demo.repository.ScheduleRepository;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.jwt.AuthEntryPointJwt;
import com.lgm.demo.security.jwt.JwtUtils;
import com.lgm.demo.security.services.UserDetailsServiceImpl;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.CompetitionService;
import com.lgm.demo.service.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompetitionController.class)
@ExtendWith(MockitoExtension.class)
public class CompetitionControllerTest {

    private final String path = "/api/v1/competition";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CompetitionService competitionService;

    // idk why I need this mocks but tests would not compile without them
    @MockBean private AuthService authService;
    @MockBean private UserDetailsServiceImpl userDetailsService;
    @MockBean private AuthEntryPointJwt authEntryPointJwt;
    @MockBean private JwtUtils jwtUtils;
    @MockBean private RoleRepository roleRepository;
    @MockBean private UserRepository userRepository;
    @MockBean private CompetitorRepository competitorRepository;
    @MockBean private ScheduleRepository scheduleRepository;
    @MockBean private ImageService imageService;

    @Test
    void GetCompetitionNames_ReturnsOk() throws Exception {
        String stringIds = "1";

        mvc.perform(get(path + "/names/{stringIds}", stringIds)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getSearchResults_ReturnsOk() throws Exception {
        String query = "ads";

        mvc.perform(get(path + "/search/{query}", query)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
