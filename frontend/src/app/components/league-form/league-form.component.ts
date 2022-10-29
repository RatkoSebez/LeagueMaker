import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LeagueDto } from 'src/app/model/dto/LeagueDto';
import { LeagueService } from 'src/app/services/league.service';

@Component({
  selector: 'app-league-form',
  templateUrl: './league-form.component.html',
  styleUrls: ['./league-form.component.css']
})
export class LeagueFormComponent implements OnInit {
  leagueForm!: FormGroup
  errorMessage = ''

  constructor(private http: HttpClient, private router: Router, private leagueService: LeagueService) { }

  ngOnInit(): void {
    this.leagueForm = new FormGroup({
      numberOfCompetitors: new FormControl('', [Validators.required, Validators.min(2), Validators.max(30)]),
      timesEachPlaysWithEach: new FormControl('', [Validators.required, Validators.min(1), Validators.max(8)]),
      daysBetweenMatches: new FormControl('', [Validators.required, Validators.min(1), Validators.max(100)]),
      competitionStart: new FormControl('', [Validators.required]),
      leagueName: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9 ]*$'), Validators.min(1), Validators.max(30)]),
      pointsWin: new FormControl('3', [Validators.required, Validators.min(1), Validators.max(100)]),
      pointsDraw: new FormControl('1', [Validators.required, Validators.min(0), Validators.max(100)]),
      pointsLose: new FormControl('0', [Validators.required, Validators.min(0), Validators.max(100)]),
    })
  }

  createLeague(){
    var formData = this.leagueForm.value
    var localStorageId = localStorage.getItem("id") || ""
    var userId = -1
    if(localStorageId != "") userId = parseInt(localStorageId)
    var leagueDto = new LeagueDto(
      formData.numberOfCompetitors,
      formData.timesEachPlaysWithEach,
      formData.competitionStart,
      formData.daysBetweenMatches,
      formData.leagueName,
      formData.pointsWin,
      formData.pointsDraw,
      formData.pointsLose,
      userId)
    this.leagueService.createLeague(leagueDto).subscribe(
      data => {
        this.errorMessage = ''
        var ids = JSON.parse(localStorage.getItem("adminOfCompetitions") || "")
        ids.push(data)
        localStorage.setItem("adminOfCompetitions", JSON.stringify(ids))
        // console.log(data)
        this.router.navigate(['/league/' + data]);
      },
      error => {
        if(error.error) this.errorMessage = error.error.message
      }
    )
  }

  get numberOfCompetitors(){
    return this.leagueForm.get('numberOfCompetitors');
  }

  get timesEachPlaysWithEach(){
    return this.leagueForm.get('timesEachPlaysWithEach');
  }

  get daysBetweenMatches(){
    return this.leagueForm.get('daysBetweenMatches');
  }

  get competitionStart(){
    return this.leagueForm.get('competitionStart');
  }

  get leagueName(){
    return this.leagueForm.get('leagueName');
  }

  get pointsWin(){
    return this.leagueForm.get('pointsWin');
  }

  get pointsDraw(){
    return this.leagueForm.get('pointsDraw');
  }

  get pointsLose(){
    return this.leagueForm.get('pointsLose');
  }
}
