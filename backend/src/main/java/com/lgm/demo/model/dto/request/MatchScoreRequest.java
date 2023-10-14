package com.lgm.demo.model.dto.request;

import com.lgm.demo.validation.annotation.CustomMatchScoreValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatchScoreRequest {
    private Long matchId;
    @CustomMatchScoreValidator
    private Integer firstCompetitorScore;
    @CustomMatchScoreValidator
    private Integer secondCompetitorScore;
}
