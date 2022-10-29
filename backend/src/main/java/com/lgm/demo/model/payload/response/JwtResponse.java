package com.lgm.demo.model.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private List<Long> adminOfCompetitions; 

    public JwtResponse(String token, Long id, String username, String email, List<String> roles, List<Long> adminOfCompetitions) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.adminOfCompetitions = adminOfCompetitions;
    }
}
