package com.lgm.demo.controller.singleelimination;

import com.lgm.demo.model.Tournament;
import com.lgm.demo.model.dto.request.MatchScoreRequest;
import com.lgm.demo.model.dto.request.TournamentRequest;
import com.lgm.demo.model.dto.response.MatchResponse;
import com.lgm.demo.validation.ValidList;
import com.lgm.demo.service.MatchService;
import com.lgm.demo.service.TournamentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/singleelimination")
public class SEController {
    private final TournamentService tournamentService;
    private final MatchService matchService;

    public SEController(@Qualifier("se") TournamentService tournamentService,
                        @Qualifier("se") MatchService matchService) {
        this.tournamentService = tournamentService;
        this.matchService = matchService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Long> createTournament(@Valid @RequestBody TournamentRequest tournamentRequest) {
        Tournament tournament = tournamentService.createTournament(tournamentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(tournament.getId());
    }

    @GetMapping("/matches/{competitionId}")
    public ResponseEntity<List<MatchResponse>> getTournamentMatches(@PathVariable Long competitionId){
        return ResponseEntity.status(HttpStatus.OK).body(tournamentService.getTournamentMatches(competitionId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/matches")
    public ResponseEntity<?> updateMatchScores(@Valid @RequestBody ValidList<MatchScoreRequest> requests){
        matchService.updateMatchScores(requests);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
