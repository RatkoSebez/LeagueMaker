package com.lgm.demo.repository;

import com.lgm.demo.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> getMatchesByCompetitionIdAndRound(Long competitionId, Integer round);

    @Query("SELECT m " +
            "from Match m " +
            "where m.competition.id = :competitionId " +
            "and (m.firstCompetitor.id = :competitorId or m.secondCompetitor.id = :competitorId) " +
            "and m.isFinished = true")
    List<Match> getPastMatchesForCompetitor(
            @Param("competitionId") Long competitionId,
            @Param("competitorId") Long competitorId);

    @Query("SELECT m " +
            "from Match m " +
            "where m.competition.id = :competitionId " +
            "and (m.firstCompetitor.id = :competitorId or m.secondCompetitor.id = :competitorId) " +
            "and m.isFinished = false")
    List<Match> getFutureMatchesForCompetitor(
            @Param("competitionId") Long competitionId,
            @Param("competitorId") Long competitorId);

    List<Match> getAllByCompetitionId(Long competitionId);

    Match getMatchByCompetitionIdAndNodeNumber(Long competitionId, Integer nodeNumber);
}
