import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LeagueStandings } from '../model/LeagueStandings';
import { LeagueDto } from '../model/dto/LeagueDto';
import { Match } from '../model/Match';

@Injectable({
  providedIn: 'root'
})
export class LeagueService {
  private path = 'http://localhost:8080/api/v1/competition/league';

  constructor(private http: HttpClient, private router: Router) { 
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  createLeague(leagueDto: LeagueDto){
    return this.http.post(this.path, leagueDto)
  }

  getStandings(leagueId: number){
    return this.http.get<LeagueStandings[]>(this.path + '/standings/' + leagueId)
  }

  getScheduleForOneRound(leagueId: number, round: number){
    return this.http.get<Match[]>(this.path + '/schedule/' + leagueId + '/' + round)
  }
}
