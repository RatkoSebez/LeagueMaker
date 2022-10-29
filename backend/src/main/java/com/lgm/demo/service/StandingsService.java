package com.lgm.demo.service;

import com.lgm.demo.model.Competition;
import com.lgm.demo.model.CompetitorStandingsLeague;

import java.util.List;

public interface StandingsService {
    List<CompetitorStandingsLeague> createLeagueStandings(Competition competition);
    List<CompetitorStandingsLeague> getLeagueStandings(Long competitionId);
}
