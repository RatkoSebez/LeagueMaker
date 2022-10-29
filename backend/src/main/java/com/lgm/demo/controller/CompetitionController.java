package com.lgm.demo.controller;

import com.lgm.demo.model.Competition;
import com.lgm.demo.service.CompetitionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/competition")
public class CompetitionController {
    private final CompetitionService competitionService;

    @GetMapping("/names/{stringIds}")
    public ResponseEntity<?> getCompetitionNames(@Valid @PathVariable String stringIds) {
        List<Long> competitionIds = competitionService.stringIdsToListIds(stringIds);
        List<String> competitionNames = competitionService.getCompetitionNames(competitionIds);
        return ResponseEntity.status(HttpStatus.OK).body(competitionNames);
    }

    @GetMapping("/rounds/{competitionId}")
    public ResponseEntity<?> getNumberOfRounds(@Valid @PathVariable Long competitionId) {
        Competition competition = competitionService.getCompetition(competitionId);
        return ResponseEntity.status(HttpStatus.OK).body(competition.getRounds());
    }
}
