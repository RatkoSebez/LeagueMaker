package com.lgm.demo.model.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private List<Long> adminOfCompetitions;
    private String profileImageInBase64;

    public JwtResponse(String token, Long id, String username, String email, List<String> roles, List<Long> adminOfCompetitions, String profileImageInBase64) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.adminOfCompetitions = adminOfCompetitions;
        this.profileImageInBase64 = profileImageInBase64;
    }
}
