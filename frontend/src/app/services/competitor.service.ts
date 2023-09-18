import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import * as myGlobals from 'src/globals';
import { CompetitorResponse } from '../model/dto/response/CompetitorResponse';

@Injectable({
  providedIn: 'root',
})
export class CompetitorService {
  private path = myGlobals.path + '/competitor';
  private subjectName = new Subject<any>();

  constructor(private http: HttpClient, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  getCompetitor(id: number) {
    return this.http.get<CompetitorResponse>(this.path + '/' + id);
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
}
