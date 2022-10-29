package com.lgm.demo.service.impl;

import com.lgm.demo.model.Competition;
import com.lgm.demo.repository.CompetitionRepository;
import com.lgm.demo.service.CompetitionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CompetitionServiceImpl implements CompetitionService {
    private CompetitionRepository competitionRepository;

    @Override
    public Competition getCompetition(Long competitionId) {
        Optional<Competition> competitionOptional = competitionRepository.getCompetitionById(competitionId);
        return competitionOptional.orElse(null);
    }

    @Override
    public List<String> getCompetitionNames(List<Long> competitionIds) {
        List<String> competitionNames = new ArrayList<>();
        for(Long id : competitionIds){
            Competition competition = getCompetition(id);
            if(competition != null)
                competitionNames.add(competition.getName());
        }
        return competitionNames;
    }

    @Override
    public List<Long> stringIdsToListIds(String stringIds){
        List<Long> competitionIds = new ArrayList<>();
        String[] split = stringIds.split("&");
        for(String s : split){
            competitionIds.add(Long.valueOf(s));
        }
        return competitionIds;
    }
}
