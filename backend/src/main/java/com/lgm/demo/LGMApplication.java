package com.lgm.demo;

import com.lgm.demo.model.*;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.repository.RoleRepository;
import com.lgm.demo.repository.ScheduleRepository;
import com.lgm.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@SpringBootApplication
public class LGMApplication implements ApplicationRunner {
	private RoleRepository roleRepository;
	private UserRepository userRepository;
	private CompetitorRepository competitorRepository;
	private ScheduleRepository scheduleRepository;

	public static void main(String[] args) {
    SpringApplication.run(LGMApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		roleRepository.save(new Role(ERole.ROLE_USER));
		roleRepository.save(new Role(ERole.ROLE_ADMIN));

		String password = "$2a$10$OK0pYmZHHCfxjSxQzVPx9eXUpVzUxOygHjEtCMrhPiqrng/nzC5Fq"; // 123456789
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(ERole.ROLE_USER));
		userRepository.save(new User("user", "user@gmail.com", password, "name", "surname", roles));

		Competitor competitor = new Competitor(null, "player1", null);
		competitorRepository.save(competitor);

		createSchedule();
	}

	private void createSchedule(){
		List<Match> matches = new ArrayList<>();
		matches.add(new Match());
		Schedule schedule = new RoundRobinSchedule(null, null, matches);
		scheduleRepository.save(schedule);
	}
}
