package com.lgm.demo;

import com.lgm.demo.model.*;
import com.lgm.demo.model.enumeration.ERole;
import com.lgm.demo.model.enumeration.ESex;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.repository.RoleRepository;
import com.lgm.demo.repository.ScheduleRepository;
import com.lgm.demo.repository.UserRepository;
import com.lgm.demo.security.Role;
import com.lgm.demo.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@SpringBootApplication
@Component
public class LGMApplication implements ApplicationRunner{
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private CompetitorRepository competitorRepository;
    private ScheduleRepository scheduleRepository;

    public static void main(String[] args){
        SpringApplication.run(LGMApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args){
        roleRepository.save(new Role(ERole.ROLE_USER));
        roleRepository.save(new Role(ERole.ROLE_ADMIN));

        String password = "$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq"; // 123456789
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        User user = new User("user", "johndoe@gmail.com", password, "John", "Doe", ESex.MALE, "Hello this is my bio.", roles);
        user.setProfileImagePath(ImageService.getRandomProfileImagePath(user));
        userRepository.save(user);

        Competitor competitor = new Competitor(null, "player1", 0, 0, 0, 0, 0, 0, 0, null);
        competitorRepository.save(competitor);

        createSchedule();
    }

    private void createSchedule(){
        List<Match> matches = new ArrayList<>();
        matches.add(new Match());
        Schedule schedule = new Schedule(null, null, matches);
        scheduleRepository.save(schedule);
    }
}
