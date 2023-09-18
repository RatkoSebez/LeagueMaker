package com.lgm.demo.model.dto.response;

import com.lgm.demo.model.Competitor;
import com.lgm.demo.model.enumeration.EResult;
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
    private Competitor firstCompetitor;
    private Competitor secondCompetitor;
    private LocalDate startTime;
    private LocalDate endTime;
    private Integer round;
    private EResult result;
    private Integer firstCompetitorScore;
    private Integer secondCompetitorScore;
    private Long competitionId;
    private Integer matchNumber;

//    public static List<MatchResponse> entityToDtoList(List<Match> matches){
//        List<MatchResponse> response = new ArrayList<>();
//        for(Match match : matches)
//            response.add(entityToDto(match));
//        return response;
//    }

    public static MatchResponse entityToDto(Match match){
        return new MatchResponse(
                match.getId(),
                match.getFirstCompetitor(),
                match.getSecondCompetitor(),
                match.getStartTime(),
                match.getEndTime(),
                match.getRound(),
                match.getResult(),
                match.getFirstCompetitorScore(),
                match.getSecondCompetitorScore(),
                match.getCompetition().getId(),
                match.getMatchNumber()
        );
    }
}
