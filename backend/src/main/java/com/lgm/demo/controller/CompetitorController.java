package com.lgm.demo.controller;

import com.lgm.demo.model.dto.response.CompetitorResponse;
import com.lgm.demo.service.CompetitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/competitor")
public class CompetitorController {
    private final CompetitorService competitorService;

    public CompetitorController(CompetitorService competitorService) {
        this.competitorService = competitorService;
    }

    @GetMapping("/{competitorId}")
    public ResponseEntity<CompetitorResponse> getCompetitor(@PathVariable Long competitorId){
        return ResponseEntity.status(HttpStatus.OK).body(competitorService.getCompetitor(competitorId));
    }
}
