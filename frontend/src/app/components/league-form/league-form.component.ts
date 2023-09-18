import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LeagueRequest } from 'src/app/model/dto/request/LeagueRequest';
import { RRService } from 'src/app/services/roundrobin/rr.service';

@Component({
  selector: 'app-league-form',
  templateUrl: './league-form.component.html',
  styleUrls: ['./league-form.component.css'],
})
export class LeagueFormComponent implements OnInit {
  leagueForm!: FormGroup;
  errorMessage = '';

  constructor(private router: Router, private leagueService: RRService) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.leagueForm = new FormGroup({
      numberOfCompetitors: new FormControl('', [
        Validators.required,
        Validators.min(3),
        Validators.max(30),
      ]),
      timesEachPlaysWithEach: new FormControl('', [
        Validators.required,
        Validators.min(1),
        Validators.max(6),
      ]),
      daysBetweenMatches: new FormControl('', [
        Validators.required,
        Validators.min(1),
      ]),
      competitionStart: new FormControl('', [Validators.required]),
      leagueName: new FormControl('', [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9 ]*$'),
        Validators.minLength(3),
        Validators.maxLength(30),
      ]),
      pointsWin: new FormControl('3', [Validators.required, Validators.min(0)]),
      pointsDraw: new FormControl('1', [
        Validators.required,
        Validators.min(0),
      ]),
      pointsLose: new FormControl('0', [
        Validators.required,
        Validators.min(0),
      ]),
    });
  }

  createLeague() {
    var formData = this.leagueForm.value;
    var request = new LeagueRequest(
      formData.numberOfCompetitors,
      formData.timesEachPlaysWithEach,
      formData.competitionStart,
      formData.daysBetweenMatches,
      formData.leagueName,
      formData.pointsWin,
      formData.pointsDraw,
      formData.pointsLose
    );
    this.leagueService.createLeague(request).subscribe({
      next: (data) => {
        this.errorMessage = '';
        var ids = JSON.parse(localStorage.getItem('adminOfCompetitions') || '');
        ids.push(data);
        localStorage.setItem('adminOfCompetitions', JSON.stringify(ids));
        this.router.navigate(['/league/' + data]);
      },
      error: (e) => {
        if (e.error) this.errorMessage = e.error.message;
      },
    });
  }
}
