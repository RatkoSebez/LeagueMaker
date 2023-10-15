package com.lgm.demo.model.dto.response;

import com.lgm.demo.security.Role;
import com.lgm.demo.model.User;
import com.lgm.demo.model.enumeration.ESex;
import com.lgm.demo.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponse{
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String bio;
    private String email;
    private Set<Role> roles = new HashSet<>();
    private List<Long> adminOfCompetitions;
    private String profileImageInBase64;
    private ESex sex;

    public static List<UserResponse> entityToDtoList(List<User> users) throws IOException{
        List<UserResponse> userResponses = new ArrayList<>();
        for(User u: users)
            userResponses.add(entityToDto(u));
        return userResponses;
    }

    public static UserResponse entityToDto(User user) throws IOException{
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getBio(),
                user.getEmail(),
                user.getRoles(),
                user.getAdminOfCompetitions(),
                ImageService.getBase64Image(user.getProfileImagePath()),
                user.getSex()
        );
    }
}
