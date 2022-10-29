package com.lgm.demo.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
@Setter
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp="^[a-zA-Z0-9_]*$")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 30)
    @Pattern(regexp="^[a-zA-Z]*$")
    private String name;

    @Size(max = 30)
    @Pattern(regexp="^[a-zA-Z]*$")
    private String surname;

    @NotBlank
    @Size(min = 8, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9!@#&]*$")
    private String password;
    // private Set<String> role;
}
