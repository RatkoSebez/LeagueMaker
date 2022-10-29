package com.lgm.demo.model.payload.response;

import com.lgm.demo.model.EResult;
import com.lgm.demo.model.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MatchResponse {
    private Long id;
    private Long firstCompetitorId;
    private Long secondCompetitorId;
    private LocalDate startTime;
    private LocalDate endTime;
    private Integer round;
    private EResult result;
    private Integer firstCompetitorScore;
    private Integer secondCompetitorScore;
    private Long competitionId;
    private String firstCompetitorName;
    private String secondCompetitorName;

    public static MatchResponse convertToMatchResponse(Match match){
        MatchResponse ret = new MatchResponse(
                match.getId(),
                match.getFirstCompetitorId(),
                match.getSecondCompetitorId(),
                match.getStartTime(),
                match.getEndTime(),
                match.getRound(),
                match.getResult(),
                match.getFirstCompetitorScore(),
                match.getSecondCompetitorScore(),
                match.getCompetitionId(),
                "",
                ""
        );
        return ret;
    }
}
