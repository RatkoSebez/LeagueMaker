package com.lgm.demo.model.dto.request;

import com.lgm.demo.validation.annotation.CustomCompetitorNameValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompetitorRequest{
    private Long id;
    @CustomCompetitorNameValidator
    private String name;
}
