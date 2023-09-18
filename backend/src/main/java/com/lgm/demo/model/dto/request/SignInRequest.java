package com.lgm.demo.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class SignInRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
