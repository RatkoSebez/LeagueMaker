package com.lgm.demo.model.payload.response;

import com.lgm.demo.model.CompetitorStandingsLeague;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompetitorStandingsLeagueResponse {
    private Long competitorId;
    private String competitorName;
    private Integer gamesPlayed;
    private Integer gamesWon;
    private Integer gamesDraw;
    private Integer gamesLost;
    private Integer points;

    public static List<CompetitorStandingsLeagueResponse> entityToDtoList(List<CompetitorStandingsLeague> standings){
        List<CompetitorStandingsLeagueResponse> standingsResponse = new ArrayList<>();
        for(CompetitorStandingsLeague s : standings)
            standingsResponse.add(entityToDto(s));
        return standingsResponse;
    }

    public static CompetitorStandingsLeagueResponse entityToDto(CompetitorStandingsLeague leagueStandings){
        return new CompetitorStandingsLeagueResponse(
                leagueStandings.getCompetitorId(),
                "",
                leagueStandings.getGamesPlayed(),
                leagueStandings.getGamesWon(),
                leagueStandings.getGamesDraw(),
                leagueStandings.getGamesLost(),
                leagueStandings.getPoints()
        );
    }
}
