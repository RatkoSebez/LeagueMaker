package com.lgm.demo.controller.roundrobin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgm.demo.model.League;
import com.lgm.demo.model.dto.request.LeagueRequest;
import com.lgm.demo.model.dto.request.MatchScoreRequest;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.repository.RoleRepository;
import com.lgm.demo.repository.ScheduleRepository;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.jwt.AuthEntryPointJwt;
import com.lgm.demo.security.jwt.JwtUtils;
import com.lgm.demo.security.services.UserDetailsServiceImpl;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.ImageService;
import com.lgm.demo.service.impl.roudrobin.RRLeagueServiceImpl;
import com.lgm.demo.service.impl.roudrobin.RRMatchServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RRController.class)
@ExtendWith(MockitoExtension.class)
public class RRControllerTest {

    private final String path = "/api/v1/roundrobin";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean private RRLeagueServiceImpl leagueService;
    @MockBean private RRMatchServiceImpl matchService;

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

    @Disabled
    @Test
    void CreateLeague_Returns201WhenRequestIsValid() throws Exception {
        List<LeagueRequest> requests = new ArrayList<>();
        requests.add(new LeagueRequest(10, 1, LocalDate.now(), 1, "name", 3, 1, 0));
        requests.add(new LeagueRequest(3, 3, LocalDate.now(), 1, "name", 3, 1, 0));
        requests.add(new LeagueRequest(10, 1, LocalDate.now(), 6, "name", 3, 1, 0));

        League response = new League(1L, null, null, null, null, null, null, null, null, null, null, null);
        when(leagueService.createLeague(ArgumentMatchers.any())).thenReturn(response);

        for(LeagueRequest request : requests){
            mvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    void CreateLeague_Returns400WhenRequestIsNotValid() throws Exception {
        List<LeagueRequest> invalidRequests = new ArrayList<>();
        invalidRequests.add(new LeagueRequest(2, 2, LocalDate.now(), 1, "name", 3, 1, 0));
        invalidRequests.add(new LeagueRequest(3, 0, LocalDate.now(), 1, "name", 3, 1, 0));
        invalidRequests.add(new LeagueRequest(10, 1, LocalDate.now(), 6, "name", -1, 1, 0));

        League response = new League(1L, null, null, null, null, null, null, null, null, null, null, null);
        when(leagueService.createLeague(ArgumentMatchers.any())).thenReturn(response);

        for(LeagueRequest request : invalidRequests){
            mvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    void GetLeagueStandingsRow_Returns200() throws Exception {
        mvc.perform(get(path + "/standings/{competitionId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void GetScheduleForOneRound_Returns200() throws Exception {
        mvc.perform(get(path + "/schedule/{competitionId}//{round}", 1L, 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void GetNumberOfRounds_Returns200() throws Exception {
        mvc.perform(get(path + "/rounds/{leagueId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void SetMatchScore_Returns200() throws Exception {
        MatchScoreRequest request = new MatchScoreRequest(1L, 2, 1);

        mvc.perform(put(path + "/match", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
