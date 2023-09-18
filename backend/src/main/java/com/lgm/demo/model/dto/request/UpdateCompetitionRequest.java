package com.lgm.demo.model.dto.request;

import com.lgm.demo.model.validation.annotation.CustomCompetitionNameValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCompetitionRequest {
    private Long competitionId;
    @CustomCompetitionNameValidator
    private String name;
    @Size(max = 1000)
    private String about;
    @Size(max = 1000)
    private String rules;
}
