package com.lgm.demo.model.dto.response;

import com.lgm.demo.model.Competition;
import com.lgm.demo.model.enumeration.ECompetitionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompetitionResponse{
    private Long id;
    private String name;
    private String about;
    private String rules;
    private Integer numberOfCompetitors;
    private ECompetitionType type;

    public static CompetitionResponse entityToDto(Competition competition, ECompetitionType type){
        return new CompetitionResponse(
                competition.getId(),
                competition.getName(),
                competition.getAbout(),
                competition.getRules(),
                competition.getNumberOfCompetitors(),
                type
        );
    }
}
