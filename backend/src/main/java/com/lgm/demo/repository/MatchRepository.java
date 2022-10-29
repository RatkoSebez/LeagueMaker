package com.lgm.demo.repository;

import com.lgm.demo.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> getMatchesByCompetitionIdAndRound(Long competitionId, Integer round);
}
