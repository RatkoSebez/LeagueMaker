package com.lgm.demo.service;

import com.lgm.demo.model.Match;
import com.lgm.demo.model.dto.request.MatchScoreRequest;
import com.lgm.demo.model.dto.response.MatchResponse;

import java.util.List;

public interface MatchService{
    List<MatchResponse> updateMatchScores(List<MatchScoreRequest> request);
    MatchResponse updateMatchScore(MatchScoreRequest request);
    // todo implement this, and use it on Competitor info page
    List<Match> getPastMatchesForCompetitor(Long competitionId, Long competitorId);
    List<Match> getFutureMatchesForCompetitor(Long competitionId, Long competitorId);
}
