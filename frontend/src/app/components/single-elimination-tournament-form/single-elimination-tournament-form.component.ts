import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TournamentRequest } from 'src/app/model/dto/request/TournamentRequest';
import { SEService } from 'src/app/services/singleelimination/se.service';

@Component({
  selector: 'app-single-elimination-tournament-form',
  templateUrl: './single-elimination-tournament-form.component.html',
  styleUrls: ['./single-elimination-tournament-form.component.css'],
})
export class SingleEliminationTournamentFormComponent implements OnInit {
  tournamentForm!: FormGroup;
  errorMessage = '';

  constructor(private router: Router, private tournamentService: SEService) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.tournamentForm = new FormGroup({
      numberOfCompetitors: new FormControl('', [
        Validators.required,
        Validators.min(2),
        Validators.max(64),
      ]),
      tournamentName: new FormControl('', [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9 ]*$'),
        Validators.minLength(3),
        Validators.maxLength(30),
      ]),
    });
  }

  createTournament() {
    var formData = this.tournamentForm.value;
    var request = new TournamentRequest(
      formData.numberOfCompetitors,
      formData.tournamentName
    );
    this.tournamentService.createTournament(request).subscribe({
      next: (data) => {
        this.errorMessage = '';
        var ids = JSON.parse(localStorage.getItem('adminOfCompetitions') || '');
        ids.push(data);
        localStorage.setItem('adminOfCompetitions', JSON.stringify(ids));
        this.router.navigate(['/tournament/' + data]);
      },
      error: (e) => {
        if (e.error) this.errorMessage = e.error.message;
      },
    });
  }

  get numberOfCompetitors() {
    return this.tournamentForm.get('numberOfCompetitors');
  }

  get tournamentName() {
    return this.tournamentForm.get('tournamentName');
  }
}
