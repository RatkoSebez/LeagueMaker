package com.lgm.demo.controller;

import com.lgm.demo.model.payload.request.CompetitorRequest;
import com.lgm.demo.service.CompetitorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/competitor")
public class CompetitorController {
    private final CompetitorService competitorService;

    @PostMapping("/name")
    public ResponseEntity<?> getCompetitionNames(@Valid @RequestBody CompetitorRequest request) {
        competitorService.setName(request);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
