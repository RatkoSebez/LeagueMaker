package com.lgm.demo.service;

import com.lgm.demo.model.Competition;
import com.lgm.demo.model.dto.request.CompetitorRequest;
import com.lgm.demo.model.dto.request.UpdateCompetitionRequest;
import com.lgm.demo.model.dto.response.CompetitionResponse;
import com.lgm.demo.model.dto.response.CompetitorResponse;

import java.util.List;

public interface CompetitionService{
    Competition getCompetition(Long competitionId);
    List<CompetitionResponse> getCompetitionNames(String competitionIdsString);
    List<CompetitionResponse> getSearchResults(String query);
    CompetitionResponse updateCompetition(UpdateCompetitionRequest request);
    void updateCompetitorNames(List<CompetitorRequest> request, Long competitionId);
    List<CompetitorResponse> getCompetitors(Long competitionId);
}
