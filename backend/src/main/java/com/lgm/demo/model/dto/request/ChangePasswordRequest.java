package com.lgm.demo.model.dto.request;

import com.lgm.demo.validation.annotation.CustomPasswordValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordRequest{
    @CustomPasswordValidator
    private String currentPassword;
    @CustomPasswordValidator
    private String newPassword;
}
