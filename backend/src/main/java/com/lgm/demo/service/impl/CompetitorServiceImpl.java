package com.lgm.demo.service.impl;

import com.lgm.demo.model.*;
import com.lgm.demo.model.payload.request.CompetitorRequest;
import com.lgm.demo.repository.CompetitionRepository;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.service.CompetitorService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CompetitorServiceImpl implements CompetitorService {
    private CompetitorRepository competitorRepository;

    @Override
    public List<Competitor> createEmptyCompetitors(Competition competition) {
        List<Competitor> competitors = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        Long ownerId = user.getId();
        for(int i=1; i<=competition.getNumberOfCompetitors(); i++){
            competitors.add(new Competitor(null, "team " + i, ownerId));
        }
        return competitors;
    }

    @Override
    public String getName(Long competitorId) {
        Competitor competitor = competitorRepository.getById(competitorId);
        return competitor.getName();
    }

    @Override
    public String setName(CompetitorRequest request) {
        Competitor competitor = competitorRepository.getById(request.getId());
        competitor.setName(request.getName());
        competitorRepository.save(competitor);
        return request.getName();
    }
}
