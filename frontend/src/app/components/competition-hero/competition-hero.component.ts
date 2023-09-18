import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompetitionResponse } from 'src/app/model/dto/response/CompetitionResponse';
import { UpdateCompetitionRequest } from 'src/app/model/dto/request/UpdateCompetitionRequest';
import { AuthService } from 'src/app/services/auth.service';
import { CompetitionService } from 'src/app/services/competition.service';
import { CompetitorService } from 'src/app/services/competitor.service';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { CompetitorRequest } from 'src/app/model/dto/request/CompetitorRequest';
import { CompetitorResponse } from 'src/app/model/dto/response/CompetitorResponse';

@Component({
  selector: 'app-competition-hero',
  templateUrl: './competition-hero.component.html',
  styleUrls: ['./competition-hero.component.css'],
})
export class CompetitionHeroComponent implements OnInit {
  competitionForm!: FormGroup;
  competitionId!: number;
  competition = new CompetitionResponse();
  isAdminOfCompetition = false;
  competitors!: CompetitorResponse[];

  constructor(
    private route: ActivatedRoute,
    private competitionService: CompetitionService,
    private competitorService: CompetitorService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.setCompetitionId();
    this.setIsAdminOfCompetition();
    this.getCompetition();
    this.getCompetitors();
  }

  initForm() {
    this.competitionForm = new FormGroup({
      name: new FormControl(this.competition.name, [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9 ]*$'),
        Validators.minLength(3),
        Validators.maxLength(30),
      ]),
      about: new FormControl(this.competition.about, [
        Validators.maxLength(1000),
      ]),
      rules: new FormControl(this.competition.rules, [
        Validators.maxLength(1000),
      ]),
      competitorNames: new FormArray([]),
    });

    if (this.competitors != null) {
      for (let competitor of this.competitors) {
        this.refCompetitorNames.controls.push(
          new FormControl(competitor.name, [
            Validators.required,
            Validators.pattern('^[a-zA-Z0-9 ]*$'),
            Validators.min(3),
            Validators.maxLength(30),
          ])
        );
      }
    }

    for (let control of this.refCompetitorNames.controls) {
      control.valueChanges.subscribe(() => {
        this.refCompetitorNames.updateValueAndValidity();
      });
    }
  }

  updateCompetition() {
    var formData = this.competitionForm.value;
    var request = new UpdateCompetitionRequest(
      this.competitionId,
      formData.name,
      formData.about,
      formData.rules
    );
    this.competitionService.updateCompetition(request).subscribe({
      next: (data) => {
        this.competition = data;
        this.initForm();
      },
    });

    var data: CompetitorRequest[] = [];
    for (let i = 0; i < this.competitors.length; i++) {
      data.push(
        new CompetitorRequest(
          this.competitors[i].id,
          this.refCompetitorNames.at(i).value
        )
      );
    }

    this.competitionService.updateNames(data, this.competitionId).subscribe({
      next: () => {
        this.competitorService.sendUpdate('');
      },
    });
  }

  setCompetitionId() {
    this.route.params.subscribe((params) => {
      this.competitionId = +params['id'];
    });
  }

  setIsAdminOfCompetition() {
    this.isAdminOfCompetition = this.authService.isAdminOfCompetition(
      this.competitionId
    );
  }

  getCompetition() {
    this.competitionService.getCompetition(this.competitionId).subscribe({
      next: (data) => {
        this.competition = data;
        this.initForm();
      },
    });
  }

  getCompetitors() {
    this.competitionService.getCompetitors(this.competitionId).subscribe({
      next: (data) => {
        this.competitors = data;
        this.initForm();
      },
    });
  }

  get refCompetitorNames() {
    return this.competitionForm.get('competitorNames') as FormArray;
  }
}
