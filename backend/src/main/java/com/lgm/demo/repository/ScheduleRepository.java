package com.lgm.demo.repository;

import com.lgm.demo.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long>{
    Schedule getScheduleByCompetitionId(Long competitionId);
}
