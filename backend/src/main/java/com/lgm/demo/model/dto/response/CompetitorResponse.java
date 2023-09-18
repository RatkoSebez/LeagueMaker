package com.lgm.demo.model.dto.response;

import com.lgm.demo.model.Competitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompetitorResponse {
    private Long id;
    private String name;
    private Integer gamesPlayed;
    private Integer gamesWon;
    private Integer gamesDraw;
    private Integer gamesLost;
    private Integer points;
    private Integer scoreObtained;
    private Integer scoreConceded;
    private List<Integer> last5games;

    public static List<CompetitorResponse> entityToDtoList(List<Competitor> competitors){
        List<CompetitorResponse> response = new ArrayList<>();
        for(Competitor competitor : competitors)
            response.add(entityToDto(competitor));
        return response;
    }

    public static CompetitorResponse entityToDto(Competitor competitor){
        return new CompetitorResponse(
                competitor.getId(),
                competitor.getName(),
                competitor.getGamesPlayed(),
                competitor.getGamesWon(),
                competitor.getGamesDraw(),
                competitor.getGamesLost(),
                competitor.getPoints(),
                competitor.getScoreObtained(),
                competitor.getScoreConceded(),
                competitor.getLast5games());
    }
}
