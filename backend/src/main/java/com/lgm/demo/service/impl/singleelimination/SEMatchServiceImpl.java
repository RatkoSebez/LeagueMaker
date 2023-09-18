package com.lgm.demo.service.impl.singleelimination;

import com.lgm.demo.model.Competitor;
import com.lgm.demo.model.Match;
import com.lgm.demo.model.Tournament;
import com.lgm.demo.model.enumeration.EResult;
import com.lgm.demo.model.dto.request.MatchScoreRequest;
import com.lgm.demo.model.dto.response.MatchResponse;
import com.lgm.demo.model.exceptions.CompetitionNotFoundException;
import com.lgm.demo.model.exceptions.InvalidMatchScoreException;
import com.lgm.demo.model.exceptions.IsNotAdminOfCompetitionException;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.repository.MatchRepository;
import com.lgm.demo.repository.TournamentRepository;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.CompetitorService;
import com.lgm.demo.service.MatchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("se")
public class SEMatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final CompetitorService competitorService;
    private final AuthService authService;

    public SEMatchServiceImpl(MatchRepository matchRepository,
                              CompetitorService competitorService,
                              AuthService authService) {
        this.matchRepository = matchRepository;
        this.competitorService = competitorService;
        this.authService = authService;
    }

    @Override
    public List<MatchResponse> updateMatchScores(List<MatchScoreRequest> requests) {
        List<MatchResponse> responses = new ArrayList<>();
        for(MatchScoreRequest request : requests){
            if(request == null) continue;
            responses.add(updateMatchScore(request));
        }
        return responses;
    }

    @Override
    public MatchResponse updateMatchScore(MatchScoreRequest request) {
        if(matchScoreRequestIsInvalid(request))
            throw new InvalidMatchScoreException(request);

        Match match = matchRepository.getById(request.getMatchId());
        if(!authService.isAdminOfCompetition(match.getCompetition().getId()))
            throw new IsNotAdminOfCompetitionException(request);

        if(match.getIsFinished() == null)
            match.setIsFinished(false);
        if(match.getIsFinished())
            competitorService.invalidateCompetitors(match);
        setMatchData(request, match);
        matchRepository.save(match);
        competitorService.updateCompetitors(match);
        Match ret = match;

        // there is no next match after final so we finish here
//        if(matchIsFinal(match))
//            return MatchResponse.entityToDto(match);

        while(!matchIsFinal(match)){
            int nextMatchNumber = match.getMatchNumber() / 2;
            Match nextMatch = matchRepository.getMatchByCompetitionIdAndMatchNumber(match.getCompetition().getId(), nextMatchNumber);
            if(nextMatch.getFirstCompetitorScore() == null || nextMatch.getSecondCompetitorScore() == null){
                setNextMatchData(match, nextMatch);
                matchRepository.save(nextMatch);
                break;
            }
            if(nextMatch.getIsFinished() == null)
                nextMatch.setIsFinished(false);
            if(nextMatch.getIsFinished())
                competitorService.invalidateCompetitors(nextMatch);
            setNextMatchData(match, nextMatch);
            matchRepository.save(nextMatch);
            competitorService.updateCompetitors(nextMatch);
            match = nextMatch;
        }

        return MatchResponse.entityToDto(ret);
    }

    @Override
    public List<Match> getPastMatchesForCompetitor(Long competitionId, Long competitorId) {
        return matchRepository.getPastMatchesForCompetitor(competitionId, competitorId);
    }

    @Override
    public List<Match> getFutureMatchesForCompetitor(Long competitionId, Long competitorId) {
        return matchRepository.getFutureMatchesForCompetitor(competitionId, competitorId);
    }

    private boolean matchScoreRequestIsInvalid(MatchScoreRequest request){
        if(request.getFirstCompetitorScore() == null || request.getSecondCompetitorScore() == null)
            return true;
        if(request.getFirstCompetitorScore().equals(request.getSecondCompetitorScore()))
            return true;
        return false;
    }

    private void setMatchData(MatchScoreRequest request, Match match){
        match.setFirstCompetitorScore(request.getFirstCompetitorScore());
        match.setSecondCompetitorScore(request.getSecondCompetitorScore());
        if(match.getFirstCompetitorScore() > match.getSecondCompetitorScore())
            match.setResult(EResult.FIRST_WON);
        else if (match.getFirstCompetitorScore() < match.getSecondCompetitorScore())
            match.setResult(EResult.SECOND_WON);
        match.setIsFinished(true);
    }

    private boolean matchIsFinal(Match match){
        return match.getMatchNumber() == 1;
    }

    private void setNextMatchData(Match match, Match nextMatch){
        Competitor winner = match.getResult() == EResult.FIRST_WON ? match.getFirstCompetitor() : match.getSecondCompetitor();

        if(match.getMatchNumber() % 2 == 0)
            nextMatch.setFirstCompetitor(winner);
        else
            nextMatch.setSecondCompetitor(winner);
    }
}
