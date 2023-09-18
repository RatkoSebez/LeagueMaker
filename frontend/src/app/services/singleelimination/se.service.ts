import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { TournamentRequest } from 'src/app/model/dto/request/TournamentRequest';
import { MatchScoreRequest } from 'src/app/model/dto/request/MatchScoreRequest';
import * as myGlobals from 'src/globals';
import { MatchResponse } from 'src/app/model/dto/response/MatchResponse';

@Injectable({
  providedIn: 'root',
})
export class SEService {
  private path = myGlobals.path + '/singleelimination';

  constructor(private http: HttpClient, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  createTournament(request: TournamentRequest) {
    return this.http.post(this.path, request);
  }

  getMatches(tournamentId: number) {
    return this.http.get<MatchResponse[]>(
      this.path + '/matches/' + tournamentId
    );
  }

  setTournamentMatchScores(setMatchScoreDto: MatchScoreRequest[]) {
    return this.http.put(this.path + '/matches', setMatchScoreDto);
  }
}
