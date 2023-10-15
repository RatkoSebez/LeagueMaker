package com.lgm.demo.service;

import com.lgm.demo.model.League;
import com.lgm.demo.model.dto.request.LeagueRequest;
import com.lgm.demo.model.dto.response.CompetitorResponse;
import com.lgm.demo.model.dto.response.MatchResponse;

import java.util.List;

public interface LeagueService{
    League createLeague(LeagueRequest leagueRequest);
    List<CompetitorResponse> getStandings(Long leagueId);
    List<MatchResponse> getSchedule(Long competitionId, Integer round);
    Integer getNumberOfRounds(Long leagueId);
}
