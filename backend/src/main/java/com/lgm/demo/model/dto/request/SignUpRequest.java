package com.lgm.demo.model.dto.request;

import com.lgm.demo.model.enumeration.ESex;
import com.lgm.demo.model.validation.annotation.CustomNameValidator;
import com.lgm.demo.model.validation.annotation.CustomPasswordValidator;
import com.lgm.demo.model.validation.annotation.CustomUsernameValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
@Setter
public class SignUpRequest {
    @CustomUsernameValidator
    private String username;
    @NotBlank
    @Email
    private String email;
    @CustomNameValidator
    private String name;
    @CustomNameValidator
    private String surname;
    @CustomPasswordValidator
    private String password;
    private ESex sex;
}
