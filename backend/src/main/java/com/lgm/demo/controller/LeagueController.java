package com.lgm.demo.controller;

import com.lgm.demo.model.*;
import com.lgm.demo.model.payload.request.LeagueRequest;
import com.lgm.demo.model.payload.response.CompetitorStandingsLeagueResponse;
import com.lgm.demo.model.payload.response.MatchResponse;
import com.lgm.demo.service.LeagueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/competition/league")
public class LeagueController {
    private final LeagueService leagueService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createLeague(@Valid @RequestBody LeagueRequest leagueRequest) {
        League league = leagueService.createLeague(leagueRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(league.getId());
    }

    @GetMapping("/standings/{competitionId}")
    public ResponseEntity<?> getStandings(@Valid @PathVariable Long competitionId) {
        List<CompetitorStandingsLeagueResponse> leagueStandingsResponses = leagueService.getStandings(competitionId);
        return ResponseEntity.status(HttpStatus.OK).body(leagueStandingsResponses);
    }

    @GetMapping("/schedule/{competitionId}/{round}")
    public ResponseEntity<?> getScheduleForOneRound(@Valid @PathVariable Long competitionId, @PathVariable Integer round) {
        List<MatchResponse> matchResponses = leagueService.getSchedule(competitionId, round);
        return ResponseEntity.status(HttpStatus.OK).body(matchResponses);
    }
}
