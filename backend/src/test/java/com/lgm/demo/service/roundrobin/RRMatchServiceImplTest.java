package com.lgm.demo.service.roundrobin;

import com.lgm.demo.model.Match;
import com.lgm.demo.model.dto.request.MatchScoreRequest;
import com.lgm.demo.model.enumeration.EResult;
import com.lgm.demo.repository.MatchRepository;
import com.lgm.demo.service.CompetitorService;
import com.lgm.demo.service.LeagueService;
import com.lgm.demo.service.impl.roudrobin.RRMatchServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RRMatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private LeagueService leagueService;
    @Mock
    private CompetitorService competitorService;

    @InjectMocks
    private RRMatchServiceImpl matchService;

    private final List<MatchScoreRequest> requests = new ArrayList<>();
    private final List<MatchScoreRequest> invalidRequests = new ArrayList<>();

    @BeforeEach
    public void init(){
        requests.add(new MatchScoreRequest(1L, 1, 2));
        requests.add(new MatchScoreRequest(1L, 0, 1));
        requests.add(new MatchScoreRequest(1L, 1, 5));
        requests.add(new MatchScoreRequest(1L, 3, 2));
        requests.add(new MatchScoreRequest(1L, 1, 0));
        requests.add(new MatchScoreRequest(1L, 0, 0));
        requests.add(new MatchScoreRequest(1L, 1, 1));
    }

    @Disabled("method is no longer in class")
    @Test
    public void SetMatchScore_MatchScoreIsSet(){
        for(MatchScoreRequest request : requests) {
            Match match = Match.builder().id(1L).nodeNumber(1).build();
            when(matchRepository.getById(request.getMatchId())).thenReturn(match);
            when(matchRepository.save(match)).thenReturn(match);

            matchService.updateMatchScore(request);

            Assertions.assertThat(match.getFirstCompetitorScore()).isEqualTo(request.getFirstCompetitorScore());
            Assertions.assertThat(match.getSecondCompetitorScore()).isEqualTo(request.getSecondCompetitorScore());
        }
    }

    @Disabled("method is no longer in class")
    @Test
    public void SetMatchScore_MatchIsFinished(){
        for(MatchScoreRequest request : requests) {
            Match match = Match.builder().id(1L).nodeNumber(1).build();
            when(matchRepository.getById(request.getMatchId())).thenReturn(match);
            when(matchRepository.save(match)).thenReturn(match);

            matchService.updateMatchScore(request);

            Assertions.assertThat(match.getIsFinished()).isTrue();
        }
    }

    @Disabled("method is no longer in class")
    @Test
    public void SetMatchScore_MatchResultIsSet(){
        for(MatchScoreRequest request : requests) {
            Match match = Match.builder().id(1L).nodeNumber(1).build();
            when(matchRepository.getById(request.getMatchId())).thenReturn(match);
            when(matchRepository.save(match)).thenReturn(match);

            matchService.updateMatchScore(request);

            Assertions.assertThat(match.getResult()).isNotNull();
        }
    }

    @Disabled("method is no longer in class")
    @Test
    public void SetMatchScore_MatchResultIsCorrect(){
        MatchScoreRequest request1 = new MatchScoreRequest(1L, 1, 0);
        MatchScoreRequest request2 = new MatchScoreRequest(1L, 1, 2);
        MatchScoreRequest request3 = new MatchScoreRequest(1L, 1, 1);

        Match match1 = Match.builder().id(1L).nodeNumber(1).build();
        when(matchRepository.getById(request1.getMatchId())).thenReturn(match1);
        matchService.updateMatchScore(request1);

        Match match2 = Match.builder().id(1L).nodeNumber(1).build();
        when(matchRepository.getById(request2.getMatchId())).thenReturn(match2);
        matchService.updateMatchScore(request2);

        Match match3 = Match.builder().id(1L).nodeNumber(1).build();
        when(matchRepository.getById(request3.getMatchId())).thenReturn(match3);
        matchService.updateMatchScore(request3);

        Assertions.assertThat(match1.getResult()).isEqualTo(EResult.FIRST_WON);
        Assertions.assertThat(match2.getResult()).isEqualTo(EResult.SECOND_WON);
        Assertions.assertThat(match3.getResult()).isEqualTo(EResult.DRAW);
    }
}
