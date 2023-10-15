package com.lgm.demo.controller;

import com.lgm.demo.service.MatchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping(path="/api/v1/competition/match")
public class MatchController{
    private final MatchService matchService;

    public MatchController(@Qualifier("rr") MatchService matchService){
        this.matchService = matchService;
    }

    // use next 2 functions for team schedule page if you will make it
    // move it to League or Tournament controller and get rid of this one

//    @GetMapping("/past/{competitionId}/{competitorId}")
//    public ResponseEntity<?> getPastMatchesForCompetitor(@PathVariable Long competitionId, @PathVariable Long competitorId) {
//        // TODO test
//        List<Match> matches = matchService.getPastMatchesForCompetitor(competitionId, competitorId);
//        return ResponseEntity.status(HttpStatus.OK).body(matches);
//    }

//    @GetMapping("/future/{competitionId}/{competitorId}")
//    public ResponseEntity<?> getFutureMatchesForCompetitor(@Valid @PathVariable Long competitionId, @PathVariable Long competitorId) {
//        // TODO test
//        List<Match> matches = matchService.getFutureMatchesForCompetitor(competitionId, competitorId);
//        return ResponseEntity.status(HttpStatus.OK).body(matches);
//    }
}
