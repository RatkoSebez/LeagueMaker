package com.lgm.demo.controller;

import com.lgm.demo.model.dto.request.CompetitorRequest;
import com.lgm.demo.model.dto.request.UpdateCompetitionRequest;
import com.lgm.demo.model.dto.response.CompetitionResponse;
import com.lgm.demo.model.dto.response.CompetitorResponse;
import com.lgm.demo.validation.ValidList;
import com.lgm.demo.service.CompetitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping(path="/api/v1/competition")
public class CompetitionController{
    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService){
        this.competitionService = competitionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionResponse> getCompetition(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(CompetitionResponse.entityToDto(competitionService.getCompetition(id), null));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping
    public ResponseEntity<CompetitionResponse> updateCompetition(@RequestBody UpdateCompetitionRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(competitionService.updateCompetition(request));
    }

    @GetMapping("/names/{stringIds}")
    public ResponseEntity<List<CompetitionResponse>> getCompetitionNames(@PathVariable String stringIds){
        return ResponseEntity.status(HttpStatus.OK).body(competitionService.getCompetitionNames(stringIds));
    }

    @GetMapping("search/{query}")
    public ResponseEntity<List<CompetitionResponse>> getSearchResults(@PathVariable String query){
        return ResponseEntity.status(HttpStatus.OK).body(competitionService.getSearchResults(query));
    }

    @GetMapping("/competitor/{competitionId}")
    public ResponseEntity<List<CompetitorResponse>> getCompetitors(@PathVariable Long competitionId){
        return ResponseEntity.status(HttpStatus.OK).body(competitionService.getCompetitors(competitionId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/competitor/names/{competitionId}")
    public ResponseEntity<?> updateCompetitorNames(@Valid @RequestBody ValidList<CompetitorRequest> request, @PathVariable Long competitionId){
        competitionService.updateCompetitorNames(request, competitionId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
