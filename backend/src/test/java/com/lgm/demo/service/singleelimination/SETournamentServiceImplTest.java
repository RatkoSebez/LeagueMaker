package com.lgm.demo.service.singleelimination;

import com.lgm.demo.model.*;
import com.lgm.demo.model.dto.request.TournamentRequest;
import com.lgm.demo.model.dto.response.MatchResponse;
import com.lgm.demo.repository.CompetitionRepository;
import com.lgm.demo.repository.MatchRepository;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.CompetitorService;
import com.lgm.demo.service.ScheduleService;
import com.lgm.demo.service.impl.singleelimination.SETournamentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SETournamentServiceImplTest {

    @Mock
    private CompetitionRepository competitionRepository;
    @Mock
    private CompetitorService competitorService;
    @Mock
    private ScheduleService scheduleService;
    @Mock
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private SETournamentServiceImpl tournamentService;


    @Test
    public void CreateTournament_DataFromTournamentRequestIsSet(){
        TournamentRequest request = new TournamentRequest(10, "name");
        User user = User.builder().adminOfCompetitions("").build();
        when(authService.getLoggedInUser()).thenReturn(user);

        Tournament tournament = tournamentService.createTournament(request);

        Assertions.assertThat(tournament.getNumberOfCompetitors()).isEqualTo(request.getNumberOfCompetitors());
        Assertions.assertThat(tournament.getName()).isEqualTo(request.getTournamentName());
    }

    @Test
    public void CreateTournament_OtherDataIsSaved(){
        TournamentRequest request = new TournamentRequest(10, "name");
        User user = User.builder().adminOfCompetitions("").build();
        when(authService.getLoggedInUser()).thenReturn(user);
        when(competitorService.createEmptyCompetitors(Mockito.any())).thenReturn(new ArrayList<>());
        when(scheduleService.createSchedule(Mockito.any())).thenReturn(new Schedule());

        Tournament tournament = tournamentService.createTournament(request);

        Assertions.assertThat(tournament.getCompetitors()).isNotNull();
        Assertions.assertThat(tournament.getSchedule()).isNotNull();
    }

    @Test
    public void GetTournamentMatches_MatchResponsesAreReturned(){
        List<Match> matches = new ArrayList<>();
        matches.add(Match.builder().nodeNumber(1).competition(new Competition()).build());
        matches.add(Match.builder().nodeNumber(2).competition(new Competition()).build());
        matches.add(Match.builder().nodeNumber(3).competition(new Competition()).build());
        Long tournamentId = 1L;
        when(matchRepository.getAllByCompetitionId(tournamentId)).thenReturn(matches);

        List<MatchResponse> responses = tournamentService.getTournamentMatches(tournamentId);

        Assertions.assertThat(responses.size()).isEqualTo(matches.size());
    }

    @Test
    public void GetTournamentMatches_MatchResponsesAreReturnedInSortedOrder(){
        List<Match> matches = new ArrayList<>();
        matches.add(Match.builder().nodeNumber(3).competition(new Competition()).build());
        matches.add(Match.builder().nodeNumber(2).competition(new Competition()).build());
        matches.add(Match.builder().nodeNumber(1).competition(new Competition()).build());
        matches.add(Match.builder().nodeNumber(5).competition(new Competition()).build());
        Long tournamentId = 1L;
        when(matchRepository.getAllByCompetitionId(tournamentId)).thenReturn(matches);


        List<MatchResponse> responses = tournamentService.getTournamentMatches(tournamentId);

        for(int i=1; i<responses.size(); i++)
            Assertions.assertThat(responses.get(i).getNodeNumber()).isGreaterThan(responses.get(i-1).getNodeNumber());
    }
}
