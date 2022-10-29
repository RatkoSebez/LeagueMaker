import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CompetitionService {
  private path = 'http://localhost:8080/api/v1/competition';

  constructor(private http: HttpClient, private router: Router) { 
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  getCompetitionNames(competitionIds: number[]){
    var stringIds: string = ''
    for(let i=0; i<competitionIds.length; i++){
      stringIds += competitionIds[i] + "&"
    }
    return this.http.get<string[]>(this.path + '/names/' + stringIds)
  }

  getNumberOfRounds(competitionId: number){
    return this.http.get<number>(this.path + '/rounds/' + competitionId)
  }
}
