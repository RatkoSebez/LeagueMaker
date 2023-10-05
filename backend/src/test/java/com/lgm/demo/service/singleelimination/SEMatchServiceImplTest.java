package com.lgm.demo.service.singleelimination;

import com.lgm.demo.model.Competition;
import com.lgm.demo.model.Competitor;
import com.lgm.demo.model.Match;
import com.lgm.demo.model.dto.request.MatchScoreRequest;
import com.lgm.demo.model.enumeration.EResult;
import com.lgm.demo.model.exceptions.InvalidMatchScoreException;
import com.lgm.demo.repository.MatchRepository;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.impl.CompetitorServiceImpl;
import com.lgm.demo.service.impl.singleelimination.SEMatchServiceImpl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SEMatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private SEMatchServiceImpl matchService;
    @Mock
    private CompetitorServiceImpl competitorService;
    @Mock
    private AuthService authService;

    private final List<MatchScoreRequest> validRequests = new ArrayList<>();
    private final List<MatchScoreRequest> invalidRequests = new ArrayList<>();

    @BeforeEach
    public void init(){
        validRequests.add(new MatchScoreRequest(1L, 1, 2));
        validRequests.add(new MatchScoreRequest(1L, 0, 1));
        validRequests.add(new MatchScoreRequest(1L, 1, 5));
        validRequests.add(new MatchScoreRequest(1L, 3, 2));
        validRequests.add(new MatchScoreRequest(1L, 1, 0));

        invalidRequests.add(new MatchScoreRequest(1L, 0, 0));
        invalidRequests.add(new MatchScoreRequest(1L, 1, 1));
        invalidRequests.add(new MatchScoreRequest(1L, 0, null));
        invalidRequests.add(new MatchScoreRequest(1L, null, null));
        invalidRequests.add(new MatchScoreRequest(1L, null, 0));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void SetMatchScore_MatchScoreIsSet(){
        Match match = Match.builder().id(1L).matchNumber(1).competition(new Competition()).build();
        Match nextMatch = Match.builder().id(2L).matchNumber(3).competition(new Competition()).build();

        for(MatchScoreRequest request : validRequests) {
            when(matchRepository.getById(request.getMatchId())).thenReturn(match);
            when(matchRepository.save(match)).thenReturn(match);
            when(matchRepository.getMatchByCompetitionIdAndNodeNumber(match.getCompetition().getId(), match.getNodeNumber())).thenReturn(nextMatch);
            when(authService.isAdminOfCompetition(Mockito.any())).thenReturn(true);
            matchService.updateMatchScore(request);

            Assertions.assertThat(match.getFirstCompetitorScore()).isEqualTo(request.getFirstCompetitorScore());
            Assertions.assertThat(match.getSecondCompetitorScore()).isEqualTo(request.getSecondCompetitorScore());
        }
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void SetMatchScore_MatchIsFinished(){
        Match match = Match.builder().id(1L).matchNumber(1).competition(new Competition()).build();
        Match nextMatch = Match.builder().id(2L).matchNumber(3).competition(new Competition()).build();

        for(MatchScoreRequest request : validRequests) {
            when(matchRepository.getById(Mockito.anyLong())).thenReturn(match);
            when(matchRepository.save(Mockito.any(Match.class))).thenReturn(match);
            when(matchRepository.getMatchByCompetitionIdAndNodeNumber(null, match.getNodeNumber())).thenReturn(nextMatch);
            doNothing().when(competitorService).updateCompetitors(match);
            when(authService.isAdminOfCompetition(Mockito.any())).thenReturn(true);
            matchService.updateMatchScore(request);

            Assertions.assertThat(match.getIsFinished()).isTrue();
        }
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void SetMatchScore_ShouldReturnNullWhenRequestIsInvalid(){
        Match match = Match.builder().id(1L).matchNumber(1).competition(new Competition()).build();
        Match nextMatch = Match.builder().id(2L).matchNumber(3).competition(new Competition()).build();

        for(MatchScoreRequest request : invalidRequests) {
            when(matchRepository.getById(Mockito.anyLong())).thenReturn(match);
            when(matchRepository.save(Mockito.any(Match.class))).thenReturn(match);
            when(matchRepository.getMatchByCompetitionIdAndNodeNumber(null, match.getNodeNumber())).thenReturn(nextMatch);

            assertThrows(InvalidMatchScoreException.class, () -> matchService.updateMatchScore(request));
        }
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void SetMatchScore_MatchResultIsGood(){
        Match match = Match.builder().id(1L).matchNumber(1).competition(new Competition()).build();
        Match nextMatch = Match.builder().id(2L).matchNumber(3).competition(new Competition()).build();

        for(MatchScoreRequest request : validRequests) {
            when(matchRepository.getById(Mockito.anyLong())).thenReturn(match);
            when(matchRepository.save(Mockito.any(Match.class))).thenReturn(match);
            when(matchRepository.getMatchByCompetitionIdAndNodeNumber(null, match.getNodeNumber())).thenReturn(nextMatch);
            when(authService.isAdminOfCompetition(Mockito.any())).thenReturn(true);
            matchService.updateMatchScore(request);

            if(match.getFirstCompetitorScore() > match.getSecondCompetitorScore())
                Assertions.assertThat(match.getResult()).isEqualTo(EResult.FIRST_WON);
            else if(match.getFirstCompetitorScore() < match.getSecondCompetitorScore())
                Assertions.assertThat(match.getResult()).isEqualTo(EResult.SECOND_WON);
        }
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void SetMatchScore_NextMatchCompetitorIdIsNotNull(){
        Match match = Match.builder().id(1L).matchNumber(2).firstCompetitor(new Competitor(4L, "name1", 0, 0, 0, 0, 0, 0, 0, null))
                .secondCompetitor(new Competitor(5L, "name2", 0, 0, 0, 0, 0, 0, 0, null))
                .competition(new Competition()).build();
        Match nextMatch = Match.builder().id(2L).matchNumber(3).competition(new Competition()).build();

        for(MatchScoreRequest request : validRequests) {
            when(matchRepository.getById(Mockito.anyLong())).thenReturn(match);
            when(matchRepository.save(Mockito.any(Match.class))).thenReturn(match);
            when(matchRepository.getMatchByCompetitionIdAndNodeNumber(null, match.getNodeNumber()/2)).thenReturn(nextMatch);
            when(authService.isAdminOfCompetition(Mockito.any())).thenReturn(true);
            matchService.updateMatchScore(request);

            Assertions.assertThat(nextMatch.getFirstCompetitor().getId() != null || nextMatch.getSecondCompetitor().getId() != null).isTrue();
        }
    }
}
