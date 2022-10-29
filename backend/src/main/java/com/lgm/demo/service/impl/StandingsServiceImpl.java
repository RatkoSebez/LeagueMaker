package com.lgm.demo.service.impl;

import com.lgm.demo.model.Competition;
import com.lgm.demo.model.Competitor;
import com.lgm.demo.model.CompetitorStandingsLeague;
import com.lgm.demo.repository.LeagueStandingsRepository;
import com.lgm.demo.service.StandingsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class StandingsServiceImpl implements StandingsService {
    private LeagueStandingsRepository leagueStandingsRepository;

    @Override
    public List<CompetitorStandingsLeague> createLeagueStandings(Competition competition) {
        List<CompetitorStandingsLeague> standings = new ArrayList<>();
        for(Competitor competitor : competition.getCompetitors()){
            standings.add(new CompetitorStandingsLeague(null, competitor.getId(), competition.getId(), 0, 0, 0, 0, 0));
        }
        return standings;
    }

    @Override
    public List<CompetitorStandingsLeague> getLeagueStandings(Long competitionId) {
        return leagueStandingsRepository.getLeagueStandingsByCompetitionnId(competitionId);
    }
}
