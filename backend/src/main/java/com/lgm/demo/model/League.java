package com.lgm.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class League extends Competition{
    private Integer timesEachPlaysWithEach;
    private Integer pointsWin;
    private Integer pointsDraw;
    private Integer pointsLose;
    private Integer rounds;
    private LocalDate competitionStart;
    private Integer daysBetweenMatches;

    public League(Long id, List<Competitor> competitors, Schedule schedule, Integer numberOfCompetitors, String name, Integer timesEachPlaysWithEach, Integer pointsWin, Integer pointsDraw, Integer pointsLose, Integer rounds, LocalDate competitionStart, Integer daysBetweenMatches){
        super(id, competitors, schedule, numberOfCompetitors, name, "", "");
        this.timesEachPlaysWithEach = timesEachPlaysWithEach;
        this.pointsWin = pointsWin;
        this.pointsDraw = pointsDraw;
        this.pointsLose = pointsLose;
        this.rounds = rounds;
        this.competitionStart = competitionStart;
        this.daysBetweenMatches = daysBetweenMatches;
    }

    public League(Integer timesEachPlaysWithEach, Integer pointsWin, Integer pointsDraw, Integer pointsLose, Integer rounds, LocalDate competitionStart, Integer daysBetweenMatches){
        this.timesEachPlaysWithEach = timesEachPlaysWithEach;
        this.pointsWin = pointsWin;
        this.pointsDraw = pointsDraw;
        this.pointsLose = pointsLose;
        this.rounds = rounds;
        this.competitionStart = competitionStart;
        this.daysBetweenMatches = daysBetweenMatches;
    }
}
