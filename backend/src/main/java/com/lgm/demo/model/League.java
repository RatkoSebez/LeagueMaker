package com.lgm.demo.model;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class League extends Competition {
    private Integer timesEachPlaysWithEach;

    public League(Long id, List<Competitor> competitors, Schedule schedule, Integer numberOfCompetitors, LocalDate competitionStart, Integer daysBetweenMatches, List<CompetitorStandingsLeague> standings, String name, Integer pointsWin, Integer pointsDraw, Integer pointsLose, Integer rounds, Integer timesEachPlaysWithEach) {
        super(id, competitors, schedule, numberOfCompetitors, competitionStart, daysBetweenMatches, standings, name, pointsWin, pointsDraw, pointsLose, rounds);
        this.timesEachPlaysWithEach = timesEachPlaysWithEach;
    }
}
