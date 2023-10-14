package com.lgm.demo.service.impl.roudrobin;

import com.lgm.demo.model.Match;
import com.lgm.demo.model.dto.request.MatchScoreRequest;
import com.lgm.demo.model.dto.response.MatchResponse;
import com.lgm.demo.model.enumeration.EResult;
import com.lgm.demo.exception.IsNotAdminOfCompetitionException;
import com.lgm.demo.repository.MatchRepository;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.CompetitorService;
import com.lgm.demo.service.MatchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("rr")
public class RRMatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final CompetitorService competitorService;
    private final AuthService authService;

    public RRMatchServiceImpl(MatchRepository matchRepository,
                              CompetitorService competitorService,
                              AuthService authService) {
        this.matchRepository = matchRepository;
        this.competitorService = competitorService;
        this.authService = authService;
    }

    @Override
    public List<MatchResponse> updateMatchScores(List<MatchScoreRequest> request) {
        return null;
    }

    @Override
    public MatchResponse updateMatchScore(MatchScoreRequest request) {
        Match match = matchRepository.getById(request.getMatchId());
        if(!authService.isAdminOfCompetition(match.getCompetition().getId()))
            throw new IsNotAdminOfCompetitionException(request);
        if(match.getIsFinished())
            competitorService.invalidateCompetitors(match);
        match.setFirstCompetitorScore(request.getFirstCompetitorScore());
        match.setSecondCompetitorScore(request.getSecondCompetitorScore());
        if(match.getFirstCompetitorScore() > match.getSecondCompetitorScore())
            match.setResult(EResult.FIRST_WON);
        else if (match.getFirstCompetitorScore() < match.getSecondCompetitorScore())
            match.setResult(EResult.SECOND_WON);
        else
            match.setResult(EResult.DRAW);

        match.setIsFinished(true);
        matchRepository.save(match);
        competitorService.updateCompetitors(match);

        return MatchResponse.entityToDto(match);
    }

    @Override
    public List<Match> getPastMatchesForCompetitor(Long competitionId, Long competitorId) {
        return matchRepository.getPastMatchesForCompetitor(competitionId, competitorId);
    }

    @Override
    public List<Match> getFutureMatchesForCompetitor(Long competitionId, Long competitorId) {
        return matchRepository.getFutureMatchesForCompetitor(competitionId, competitorId);
    }
}
