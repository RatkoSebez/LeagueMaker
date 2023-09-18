package com.lgm.demo.model.dto.request;

import com.lgm.demo.model.validation.annotation.CustomPasswordValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeEmailRequest {
    @CustomPasswordValidator
    private String password;
    @NotBlank
    @Email
    private String newEmail;
}
