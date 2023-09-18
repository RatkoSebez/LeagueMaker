package com.lgm.demo.service.impl;

import com.lgm.demo.model.Competition;
import com.lgm.demo.model.Competitor;
import com.lgm.demo.model.League;
import com.lgm.demo.model.Tournament;
import com.lgm.demo.model.dto.request.CompetitorRequest;
import com.lgm.demo.model.dto.request.UpdateCompetitionRequest;
import com.lgm.demo.model.dto.response.CompetitionResponse;
import com.lgm.demo.model.dto.response.CompetitorResponse;
import com.lgm.demo.model.enumeration.ECompetitionType;
import com.lgm.demo.model.exceptions.CompetitionNotFoundException;
import com.lgm.demo.model.exceptions.IsNotAdminOfCompetitionException;
import com.lgm.demo.repository.CompetitionRepository;
import com.lgm.demo.service.AuthService;
import com.lgm.demo.service.CompetitionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final AuthService authService;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository, AuthService authService) {
        this.competitionRepository = competitionRepository;
        this.authService = authService;
    }

    @Override
    public Competition getCompetition(Long competitionId) {
        return competitionRepository.getCompetitionById(competitionId)
                .orElseThrow(() -> new CompetitionNotFoundException(competitionId));
    }

    @Override
    public List<CompetitionResponse> getCompetitionNames(String request) {
        List<Long> competitionIds = stringIdsToListIds(request);
        List<CompetitionResponse> competitionNames = new ArrayList<>();
        for(Long id : competitionIds){
            Competition competition = getCompetition(id);

            Tournament tournament = competition instanceof Tournament ? ((Tournament) competition) : null;
            League league = competition instanceof League ? ((League) competition) : null;

            if(competition != null) {
                if(league != null)
                    competitionNames.add(CompetitionResponse.entityToDto(competition, ECompetitionType.LEAGUE));
                if(tournament != null)
                    competitionNames.add(CompetitionResponse.entityToDto(competition, ECompetitionType.TOURNAMENT));
            }
        }
        return competitionNames;
    }

    @Override
    public List<CompetitionResponse> getSearchResults(String query) {
        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<Competition> results = competitionRepository.findMatchingSearchResults(query, pageRequest);
        List<Competition> competitions = results.getContent();
        if(competitions.size() == 0) {
            results = competitionRepository.findSimilarSearchResults(query, pageRequest);
            competitions = results.getContent();
        }

        List<CompetitionResponse> response = new ArrayList<>();
        for(Competition competition : competitions){
            Tournament tournament = competition instanceof Tournament ? ((Tournament) competition) : null;
            League league = competition instanceof League ? ((League) competition) : null;
            if(tournament != null)
                response.add(CompetitionResponse.entityToDto(competition, ECompetitionType.TOURNAMENT));
            if(league != null)
                response.add(CompetitionResponse.entityToDto(competition, ECompetitionType.LEAGUE));
        }

        return response;
    }

    @Override
    public CompetitionResponse updateCompetition(UpdateCompetitionRequest request) {
        if(!authService.isAdminOfCompetition(request.getCompetitionId()))
            throw new IsNotAdminOfCompetitionException(request);
        Competition competition = competitionRepository.getCompetitionById(request.getCompetitionId())
                .orElseThrow(() -> new CompetitionNotFoundException(request));
        competition.setAbout(request.getAbout());
        competition.setRules(request.getRules());
        competition.setName(request.getName());
        competitionRepository.save(competition);
        return CompetitionResponse.entityToDto(competition, null);
    }

    private List<Long> stringIdsToListIds(String stringIds){
        List<Long> competitionIds = new ArrayList<>();
        String[] split = stringIds.split("&");
        for(String s : split){
            competitionIds.add(Long.valueOf(s));
        }
        return competitionIds;
    }

    @Override
    public void updateCompetitorNames(List<CompetitorRequest> request, Long competitionId) {
        if(!authService.isAdminOfCompetition(competitionId))
            throw new IsNotAdminOfCompetitionException(request);
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new CompetitionNotFoundException(competitionId));
        Map<Long, String> requestAsMap = new HashMap<>();
        for(CompetitorRequest cr : request)
            requestAsMap.put(cr.getId(), cr.getName());

        List<Competitor> competitors = competition.getCompetitors();
        for(Competitor competitor : competitors){
            String newName = requestAsMap.get(competitor.getId());
            if(newName == null || newName.equals(competitor.getName())) continue;
            competitor.setName(newName);
        }
        competitionRepository.save(competition);
    }

    @Override
    public List<CompetitorResponse> getCompetitors(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new CompetitionNotFoundException(competitionId));
        return CompetitorResponse.entityToDtoList(competition.getCompetitors());
    }
}
