import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CompetitorDto } from '../model/dto/CompetitorDto';

@Injectable({
  providedIn: 'root'
})
export class CompetitorService {
  private path = 'http://localhost:8080/api/v1/competitor';

  constructor(private http: HttpClient, private router: Router) { 
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  editName(competitorDto: CompetitorDto){
    return this.http.post(this.path + '/name', competitorDto)
  }
}
