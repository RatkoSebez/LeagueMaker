package com.lgm.demo.model.dto.request;

import com.lgm.demo.validation.annotation.CustomCompetitionNameValidator;
import com.lgm.demo.validation.annotation.CustomNumberOfCompetitorsInLeagueValidator;
import com.lgm.demo.validation.annotation.CustomTimesEachPlaysWithEachValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeagueRequest{
    @CustomNumberOfCompetitorsInLeagueValidator
    private Integer numberOfCompetitors;
    @CustomTimesEachPlaysWithEachValidator
    private Integer timesEachPlaysWithEach;
    private LocalDate competitionStart;
    @Positive
    private Integer daysBetweenMatches;
    @CustomCompetitionNameValidator
    private String leagueName;
    @PositiveOrZero
    @NotNull
    private Integer pointsWin;
    @PositiveOrZero
    @NotNull
    private Integer pointsDraw;
    @PositiveOrZero
    @NotNull
    private Integer pointsLose;
}
