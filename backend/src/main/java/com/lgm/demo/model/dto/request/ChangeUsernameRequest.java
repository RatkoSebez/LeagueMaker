package com.lgm.demo.model.dto.request;

import com.lgm.demo.validation.annotation.CustomUsernameValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeUsernameRequest {
    @CustomUsernameValidator
    private String newUsername;
    private String password;
}
