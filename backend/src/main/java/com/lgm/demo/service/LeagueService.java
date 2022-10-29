package com.lgm.demo.service;

import com.lgm.demo.model.League;
import com.lgm.demo.model.payload.request.LeagueRequest;
import com.lgm.demo.model.payload.response.CompetitorStandingsLeagueResponse;
import com.lgm.demo.model.payload.response.MatchResponse;

import java.util.List;

public interface LeagueService {
    League createLeague(LeagueRequest leagueRequest);
    List<CompetitorStandingsLeagueResponse> getStandings(Long leagueId);
    List<MatchResponse> getSchedule(Long competitionId, Integer round);
}
