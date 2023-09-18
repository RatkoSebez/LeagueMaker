package com.lgm.demo.controller.singleelimination;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgm.demo.model.Tournament;
import com.lgm.demo.model.dto.request.MatchScoreRequest;
import com.lgm.demo.model.dto.request.TournamentRequest;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.repository.RoleRepository;
import com.lgm.demo.repository.ScheduleRepository;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.jwt.AuthEntryPointJwt;
import com.lgm.demo.security.jwt.JwtUtils;
import com.lgm.demo.security.services.UserDetailsServiceImpl;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.ImageService;
import com.lgm.demo.service.impl.singleelimination.SEMatchServiceImpl;
import com.lgm.demo.service.impl.singleelimination.SETournamentServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SEController.class)
@ExtendWith(MockitoExtension.class)
public class SEControllerTest {

    private final String path = "/api/v1/singleelimination";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean private SETournamentServiceImpl tournamentService;
    @MockBean private SEMatchServiceImpl matchService;

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
    void CreateTournament_Returns201WhenRequestIsValid() throws Exception {
        List<TournamentRequest> requests = new ArrayList<>();
        requests.add(new TournamentRequest(12, "name"));
        requests.add(new TournamentRequest(2, "name"));
        requests.add(new TournamentRequest(24, "name"));
        requests.add(new TournamentRequest(64, "name"));
//        requests.add(new TournamentRequest(4, "n"));

        Tournament response = new Tournament(1L, null, null, null, null, null);
        when(tournamentService.createTournament(ArgumentMatchers.any())).thenReturn(response);

        for(TournamentRequest request : requests){
            mvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    void CreateTournament_Returns400WhenRequestIsNotValid() throws Exception {
        List<TournamentRequest> invalidRequests = new ArrayList<>();
        invalidRequests.add(new TournamentRequest(12, ""));
        invalidRequests.add(new TournamentRequest(2, null));
        invalidRequests.add(new TournamentRequest(65, "name"));
        invalidRequests.add(new TournamentRequest(1, "name"));

        Tournament response = new Tournament(1L, null, null, null, null, null);
        when(tournamentService.createTournament(ArgumentMatchers.any())).thenReturn(response);

        for(TournamentRequest request : invalidRequests){
            mvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    void GetTournamentMatches_ReturnsOk() throws Exception {
        when(tournamentService.getTournamentMatches(ArgumentMatchers.any())).thenReturn(new ArrayList<>());

        mvc.perform(get(path + "/matches/{competitionId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void SetMatchScores_Returns200WhenRequestIsValid() throws Exception {
        List<MatchScoreRequest> requests = new ArrayList<>();
        requests.add(new MatchScoreRequest(1L, 1, 0));
        requests.add(new MatchScoreRequest(1L, 1, 1));
        requests.add(new MatchScoreRequest(1L, 1, 3));
        requests.add(new MatchScoreRequest(1L, 7, 6));

        mvc.perform(put(path + "/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requests)))
                .andExpect(status().isOk());
    }

    @Test
    void SetMatchScores_Returns404WhenRequestIsNotValid() throws Exception {
        List<MatchScoreRequest> invalidRequests = new ArrayList<>();
        invalidRequests.add(new MatchScoreRequest(1L, 1, -1));
        invalidRequests.add(new MatchScoreRequest(1L, -1, -1));
        invalidRequests.add(new MatchScoreRequest(1L, -1, 3));

        mvc.perform(put(path + "/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequests)))
                .andExpect(status().isBadRequest());
    }
}
