package com.lgm.demo.service;

import com.lgm.demo.model.Competitor;
import com.lgm.demo.model.Match;
import com.lgm.demo.model.dto.response.CompetitorResponse;

import java.util.List;

public interface CompetitorService{
    CompetitorResponse getCompetitor(Long competitorId);
    List<Competitor> createEmptyCompetitors(Integer numberOfCompetitors);
    void updateCompetitors(Match match);
    void invalidateCompetitors(Match match);
}
