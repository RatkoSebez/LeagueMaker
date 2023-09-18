import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CompetitionResponse } from '../model/dto/response/CompetitionResponse';
import { UpdateCompetitionRequest } from '../model/dto/request/UpdateCompetitionRequest';
import { CompetitorResponse } from '../model/dto/response/CompetitorResponse';
import * as myGlobals from 'src/globals';
import { CompetitorRequest } from '../model/dto/request/CompetitorRequest';

@Injectable({
  providedIn: 'root',
})
export class CompetitionService {
  private path = myGlobals.path + '/competition';

  constructor(private http: HttpClient, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  getCompetitionNames(competitionIds: number[]) {
    var stringIds: string = '';
    for (let i = 0; i < competitionIds.length; i++) {
      stringIds += competitionIds[i] + '&';
    }
    return this.http.get<CompetitionResponse[]>(
      this.path + '/names/' + stringIds
    );
  }

  getSearchResults(query: string) {
    return this.http.get<CompetitionResponse[]>(this.path + '/search/' + query);
  }

  getCompetition(id: number) {
    return this.http.get<CompetitionResponse>(this.path + '/' + id);
  }

  updateCompetition(request: UpdateCompetitionRequest) {
    return this.http.put<CompetitionResponse>(this.path, request);
  }

  updateNames(competitorDto: CompetitorRequest[], competitionId: number) {
    return this.http.put(
      this.path + '/competitor/names/' + competitionId,
      competitorDto
    );
  }

  getCompetitors(competitionId: number) {
    return this.http.get<CompetitorResponse[]>(
      this.path + '/competitor/' + competitionId
    );
  }
}
