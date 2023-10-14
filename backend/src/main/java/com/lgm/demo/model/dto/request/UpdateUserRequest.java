package com.lgm.demo.model.dto.request;

import com.lgm.demo.validation.annotation.CustomNameValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    @CustomNameValidator
    private String name;
    @CustomNameValidator
    private String surname;
    @Size(max = 500)
    private String bio;
}
