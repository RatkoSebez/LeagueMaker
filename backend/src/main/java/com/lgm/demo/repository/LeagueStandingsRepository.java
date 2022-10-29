package com.lgm.demo.repository;

import com.lgm.demo.model.CompetitorStandingsLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueStandingsRepository extends JpaRepository<CompetitorStandingsLeague, Long> {
    List<CompetitorStandingsLeague> getLeagueStandingsByCompetitionnId(Long id);
}
