package com.lgm.demo.service.roundrobin;

import com.lgm.demo.model.*;
import com.lgm.demo.model.dto.request.LeagueRequest;
import com.lgm.demo.model.dto.response.CompetitorResponse;
import com.lgm.demo.model.dto.response.MatchResponse;
import com.lgm.demo.model.enumeration.EResult;
import com.lgm.demo.repository.*;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.CompetitorService;
import com.lgm.demo.service.ScheduleService;
import com.lgm.demo.service.impl.roudrobin.RRLeagueServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RRLeagueServiceImplTest {

    @Mock
    private CompetitorService competitorService;
    @Mock
    private ScheduleService scheduleService;
    @Mock
    private CompetitionRepository competitionRepository;
    @Mock
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MatchRepository matchRepository;
    @Mock
    private CompetitorRepository competitorRepository;
    @Mock
    private LeagueRepository leagueRepository;

    @InjectMocks
    private RRLeagueServiceImpl leagueService;

    @Test
    public void CreateLeague_DataFromLeagueRequestIsSet(){
        LeagueRequest request = new LeagueRequest(10, 1, LocalDate.now(), 7, "name", 3, 1, 0);
        User user = User.builder().adminOfCompetitions("").build();
        when(authService.getLoggedInUser()).thenReturn(user);

        League league = leagueService.createLeague(request);

        Assertions.assertThat(league.getNumberOfCompetitors()).isEqualTo(request.getNumberOfCompetitors());
        Assertions.assertThat(league.getName()).isEqualTo(request.getLeagueName());
    }

    @Test
    public void CreateLeague_OtherDataIsSaved(){
        LeagueRequest request = new LeagueRequest(10, 1, LocalDate.now(), 7, "name", 3, 1, 0);
        User user = User.builder().adminOfCompetitions("").build();
        when(authService.getLoggedInUser()).thenReturn(user);
        when(competitorService.createEmptyCompetitors(Mockito.any())).thenReturn(new ArrayList<>());
        when(scheduleService.createSchedule(Mockito.any())).thenReturn(new Schedule());

        League league = leagueService.createLeague(request);

        Assertions.assertThat(league.getCompetitors()).isNotNull();
        Assertions.assertThat(league.getSchedule()).isNotNull();
    }

    @Test
    public void GetStandings_ResponseIsReturned(){
        Long leagueId = 1L;
        Competition competition = new Competition();
        when(competitionRepository.getCompetitionById(leagueId)).thenReturn(Optional.of(competition));

        List<CompetitorResponse> responses = leagueService.getStandings(leagueId);

        Assertions.assertThat(responses.size()).isEqualTo(competition.getCompetitors().size());
    }

    @Test
    public void GetSchedule_ScheduleIsReturned(){
        Long leagueId = 1L;
        Integer round = 2;
        List<Match> matches = new ArrayList<>(2);
        when(matchRepository.getMatchesByCompetitionIdAndRound(leagueId, round)).thenReturn(matches);

        List<MatchResponse> response = leagueService.getSchedule(leagueId, round);

        Assertions.assertThat(response.size()).isEqualTo(matches.size());
    }

    @Test
    public void GetNumberOfRounds_ReturnedValueIsCorrect(){
        Long leagueId = 1L;
        int numberOfRounds = 3;
        League league = new League();
        league.setRounds(numberOfRounds);
        when(leagueRepository.getById(leagueId)).thenReturn(league);

        Integer response = leagueService.getNumberOfRounds(leagueId);

        Assertions.assertThat(response).isEqualTo(league.getRounds());
    }

    @Disabled("method is no longer in class")
    @Test
    public void UpdateStandings_ScoreIsSetWhenFirstWon(){
        Match match = Match.builder()
                .result(EResult.FIRST_WON).
                firstCompetitor(new Competitor(1L, "name1", 0, 0, 0, 0, 0, 0, 0, null)).
                secondCompetitor(new Competitor(2L, "name1", 0, 0, 0, 0, 0, 0, 0, null)).
                build();
        EResult previousResult = null;
        League league = new League();
        league.setPointsWin(3);
        league.setPointsDraw(1);
        league.setPointsLose(0);

        Competitor firstRow = Competitor.builder()
                .gamesWon(0)
                .gamesDraw(0)
                .gamesLost(0)
                .points(0)
                .gamesPlayed(0).build();
        Competitor secondRow = Competitor.builder()
                .gamesWon(0)
                .gamesDraw(0)
                .gamesLost(0)
                .points(0)
                .gamesPlayed(0).build();

        when(competitionRepository.getById(match.getCompetition().getId())).thenReturn(league);
        when(competitorRepository.getById(match.getFirstCompetitor().getId())).thenReturn(firstRow);
        when(competitorRepository.getById(match.getSecondCompetitor().getId())).thenReturn(secondRow);

        // todo premesti ove testove u competitorService
//        leagueService.updateCompetitors(match, false);

        Assertions.assertThat(firstRow.getGamesWon()).isEqualTo(1);
        Assertions.assertThat(secondRow.getGamesLost()).isEqualTo(1);
    }

    @Disabled("method is no longer in class")
    @Test
    public void UpdateStandings_UpdateResultWhenThereWasPreviousResult(){
        Match match = Match.builder()
                .result(EResult.FIRST_WON).
                firstCompetitor(new Competitor(1L, "name1", 0, 0, 0, 0, 0, 0, 0, null)).
                secondCompetitor(new Competitor(2L, "name1", 0, 0, 0, 0, 0, 0, 0, null)).
                build();
        EResult previousResult = EResult.SECOND_WON;
        League league = new League();
        league.setPointsWin(3);
        league.setPointsDraw(1);
        league.setPointsLose(0);

        Competitor firstRow = Competitor.builder()
                .gamesWon(0)
                .gamesDraw(0)
                .gamesLost(1)
                .points(0)
                .gamesPlayed(1).build();
        Competitor secondRow = Competitor.builder()
                .gamesWon(1)
                .gamesDraw(0)
                .gamesLost(0)
                .points(3)
                .gamesPlayed(1).build();

        when(competitionRepository.getById(match.getCompetition().getId())).thenReturn(league);
        when(competitorRepository.getById(match.getFirstCompetitor().getId())).thenReturn(firstRow);
        when(competitorRepository.getById(match.getSecondCompetitor().getId())).thenReturn(secondRow);

//        leagueService.updateCompetitors(match, true);

        Assertions.assertThat(firstRow.getGamesWon()).isEqualTo(1);
        Assertions.assertThat(firstRow.getGamesLost()).isEqualTo(0);
        Assertions.assertThat(secondRow.getGamesLost()).isEqualTo(1);
        Assertions.assertThat(secondRow.getGamesWon()).isEqualTo(0);
    }

    @Disabled("method is no longer in class")
    @Test
    public void UpdateStandings_ScoreIsSetWhenResultIsDraw(){
        Match match = Match.builder()
                .result(EResult.DRAW).
                firstCompetitor(new Competitor(1L, "name1", 0, 0, 0, 0, 0, 0, 0, null)).
                secondCompetitor(new Competitor(2L, "name1", 0, 0, 0, 0, 0, 0, 0, null)).
                competition(new Competition()).
                build();
        EResult previousResult = null;
        League league = new League();
        league.setPointsWin(3);
        league.setPointsDraw(1);
        league.setPointsLose(0);

        Competitor firstRow = Competitor.builder()
                .gamesWon(0)
                .gamesDraw(0)
                .gamesLost(0)
                .points(0)
                .gamesPlayed(0).build();
        Competitor secondRow = Competitor.builder()
                .gamesWon(0)
                .gamesDraw(0)
                .gamesLost(0)
                .points(0)
                .gamesPlayed(0).build();

        when(competitionRepository.getById(match.getCompetition().getId())).thenReturn(league);
        when(competitorRepository.getById(match.getFirstCompetitor().getId())).thenReturn(firstRow);
        when(competitorRepository.getById(match.getSecondCompetitor().getId())).thenReturn(secondRow);
//        leagueService.updateCompetitors(match, true);

        Assertions.assertThat(firstRow.getGamesDraw()).isEqualTo(1);
        Assertions.assertThat(secondRow.getGamesDraw()).isEqualTo(1);
    }
}
