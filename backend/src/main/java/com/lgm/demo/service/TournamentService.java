package com.lgm.demo.service;

import com.lgm.demo.model.Match;
import com.lgm.demo.model.Tournament;
import com.lgm.demo.model.dto.request.TournamentRequest;
import com.lgm.demo.model.dto.response.MatchResponse;

import java.util.List;

public interface TournamentService{
    Tournament createTournament(TournamentRequest tournamentRequest);
    List<MatchResponse> getTournamentMatches(Long tournamentId);
}
