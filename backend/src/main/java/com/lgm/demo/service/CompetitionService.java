package com.lgm.demo.service;

import com.lgm.demo.model.Competition;

import java.util.List;

public interface CompetitionService {
    Competition getCompetition(Long competitionId);
    List<String> getCompetitionNames(List<Long> competitionIds);
    List<Long> stringIdsToListIds(String stringIds);
}
