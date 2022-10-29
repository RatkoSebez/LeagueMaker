package com.lgm.demo.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeagueRequest {
    private Integer numberOfCompetitors;
    private Integer timesEachPlaysWithEach;
    private LocalDate competitionStart;
    private Integer daysBetweenMatches;
    private String leagueName;
    private Integer pointsWin;
    private Integer pointsDraw;
    private Integer pointsLose;
    private Integer userId;
}
