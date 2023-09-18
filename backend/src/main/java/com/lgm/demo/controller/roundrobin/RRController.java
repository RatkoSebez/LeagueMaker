package com.lgm.demo.controller.roundrobin;

import com.lgm.demo.model.League;
import com.lgm.demo.model.dto.request.LeagueRequest;
import com.lgm.demo.model.dto.request.MatchScoreRequest;
import com.lgm.demo.model.dto.response.CompetitorResponse;
import com.lgm.demo.model.dto.response.MatchResponse;
import com.lgm.demo.service.LeagueService;
import com.lgm.demo.service.MatchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/roundrobin")
public class RRController {
    private final LeagueService leagueService;
    private final MatchService matchService;

    public RRController(@Qualifier("rr") LeagueService leagueService,
                        @Qualifier("rr") MatchService matchService) {
        this.leagueService = leagueService;
        this.matchService = matchService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Long> createLeague(@Valid @RequestBody LeagueRequest leagueRequest) {
        League league = leagueService.createLeague(leagueRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(league.getId());
    }

    @GetMapping("/standings/{competitionId}")
    public ResponseEntity<List<CompetitorResponse>> getLeagueStandingsRows(@PathVariable Long competitionId) {
        return ResponseEntity.status(HttpStatus.OK).body(leagueService.getStandings(competitionId));
    }

    @GetMapping("/schedule/{competitionId}/{round}")
    public ResponseEntity<List<MatchResponse>> getScheduleForOneRound(@PathVariable Long competitionId,
                                                                      @PathVariable Integer round) {
        return ResponseEntity.status(HttpStatus.OK).body(leagueService.getSchedule(competitionId, round));
    }

    @GetMapping("/rounds/{leagueId}")
    public ResponseEntity<Integer> getNumberOfRounds(@PathVariable Long leagueId) {
        return ResponseEntity.status(HttpStatus.OK).body(leagueService.getNumberOfRounds(leagueId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/match")
    public ResponseEntity<MatchResponse> updateMatchScore(@Valid @RequestBody MatchScoreRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(matchService.updateMatchScore(request));
    }
}
