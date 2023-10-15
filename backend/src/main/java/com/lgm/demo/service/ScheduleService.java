package com.lgm.demo.service;

import com.lgm.demo.model.Competition;
import com.lgm.demo.model.League;
import com.lgm.demo.model.Schedule;
import com.lgm.demo.model.Tournament;

public interface ScheduleService{
    Schedule createSchedule(Competition competition);
}
