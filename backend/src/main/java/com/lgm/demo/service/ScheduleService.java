package com.lgm.demo.service;

import com.lgm.demo.model.League;
import com.lgm.demo.model.RoundRobinSchedule;
import com.lgm.demo.model.Schedule;

public interface ScheduleService {
    RoundRobinSchedule createRoundRobinSchedule(League league);
    Schedule getSchedule(Long competitionId);
}
