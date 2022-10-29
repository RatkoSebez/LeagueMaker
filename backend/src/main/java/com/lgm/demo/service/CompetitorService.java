package com.lgm.demo.service;

import com.lgm.demo.model.Competition;
import com.lgm.demo.model.Competitor;
import com.lgm.demo.model.payload.request.CompetitorRequest;

import java.util.List;

public interface CompetitorService {
    List<Competitor> createEmptyCompetitors(Competition competition);
    String getName(Long competitorId);
    String setName(CompetitorRequest request);
}
