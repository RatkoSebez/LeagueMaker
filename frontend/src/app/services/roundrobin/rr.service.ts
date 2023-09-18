import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { LeagueRequest } from 'src/app/model/dto/request/LeagueRequest';
import { MatchScoreRequest } from 'src/app/model/dto/request/MatchScoreRequest';
import * as myGlobals from 'src/globals';
import { MatchResponse } from 'src/app/model/dto/response/MatchResponse';
import { CompetitorResponse } from 'src/app/model/dto/response/CompetitorResponse';

@Injectable({
  providedIn: 'root',
})
export class RRService {
  private path = myGlobals.path + '/roundrobin';
  private subjectName = new Subject<any>();

  constructor(private http: HttpClient, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  // next 2 functions are used for comminucation between league-schedule and league-standings components
  sendUpdate(message: string) {
    //the component that wants to update something, calls this fn
    this.subjectName.next({ text: message }); //next() will feed the value in Subject
  }

  getUpdate(): Observable<any> {
    //the receiver component calls this function
    return this.subjectName.asObservable(); //it returns as an observable to which the receiver funtion will subscribe
  }

  createLeague(leagueDto: LeagueRequest) {
    return this.http.post(this.path, leagueDto);
  }

  getStandings(leagueId: number) {
    return this.http.get<CompetitorResponse[]>(
      this.path + '/standings/' + leagueId
    );
  }

  getScheduleForOneRound(leagueId: number, round: number) {
    return this.http.get<MatchResponse[]>(
      this.path + '/schedule/' + leagueId + '/' + round
    );
  }

  getNumberOfRounds(competitionId: number) {
    return this.http.get<number>(this.path + '/rounds/' + competitionId);
  }

  setMatchScore(setMatchScoreDto: MatchScoreRequest) {
    return this.http.put<MatchResponse>(this.path + '/match', setMatchScoreDto);
  }
}
