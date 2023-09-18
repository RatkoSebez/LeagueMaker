import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MatchScoreRequest } from 'src/app/model/dto/request/MatchScoreRequest';
import { MatchResponse } from 'src/app/model/dto/response/MatchResponse';
import { AuthService } from 'src/app/services/auth.service';
import { CompetitorService } from 'src/app/services/competitor.service';
import { SEService } from 'src/app/services/singleelimination/se.service';

@Component({
  selector: 'app-tournament-brackets',
  templateUrl: './tournament-brackets.component.html',
  styleUrls: ['./tournament-brackets.component.css'],
})
export class TournamentBracketsComponent implements OnInit {
  matchesForm!: FormGroup;
  competitionId!: number;
  matches!: MatchResponse[];
  rounds: MatchResponse[][] = [];
  numberOfRounds = 0;
  padding_top = ['0px', 'px', '40px', '100px', '220px', '460px', '940px'];
  padding_bottom = ['0px', '0px', '80px', '140px', '260px', '500px', '0px'];
  rounds_strings = [''];
  formArrayNames = [
    'matchesr1',
    'matchesr2',
    'matchesr3',
    'matchesr4',
    'matchesr5',
    'matchesr6',
  ];
  refMatches = [new FormArray<any>([])];
  currentRound = 1;
  isAdminOfCompetition = false;
  map = new Map<number, MatchResponse>();

  constructor(
    private route: ActivatedRoute,
    private tournamentService: SEService,
    private authService: AuthService,
    private competitorService: CompetitorService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.getCompetitionIdFromRoute();
    this.setIsAdminOfCompetition();

    // subscribe competition-hero
    this.competitorService.getUpdate().subscribe(() => {
      this.updateMatches();
    });
  }

  getCompetitionIdFromRoute() {
    this.route.params.subscribe({
      next: (params) => {
        this.competitionId = +params['id'];
        this.updateMatches();
      },
    });
  }

  setIsAdminOfCompetition() {
    this.isAdminOfCompetition = this.authService.isAdminOfCompetition(
      this.competitionId
    );
  }

  initForm() {
    this.matchesForm = new FormGroup({
      matchesr1: new FormArray([]),
      matchesr2: new FormArray([]),
      matchesr3: new FormArray([]),
      matchesr4: new FormArray([]),
      matchesr5: new FormArray([]),
      matchesr6: new FormArray([]),
    });

    this.refMatches.push(this.refMatchesr1);
    this.refMatches.push(this.refMatchesr2);
    this.refMatches.push(this.refMatchesr3);
    this.refMatches.push(this.refMatchesr4);
    this.refMatches.push(this.refMatchesr5);
    this.refMatches.push(this.refMatchesr6);

    if (this.rounds == null) return;

    for (let i = 1; i < this.rounds.length; i++) {
      for (let j = 0; j < this.rounds[i].length; j++) {
        this.refMatches[i].controls.push(
          new FormControl(this.rounds[i][j].firstCompetitorScore)
        );
        this.refMatches[i].controls.push(
          new FormControl(this.rounds[i][j].secondCompetitorScore)
        );
      }
    }
  }

  public updateMatches() {
    this.tournamentService.getMatches(this.competitionId).subscribe((data) => {
      this.matches = data;
      this.setMap();
      this.setRounds();
      this.initForm();
      this.setNumberOfRounds();
      this.setRoundsStrings();
    });
  }

  setRounds() {
    this.rounds = [];
    for (let i = 0; i < this.matches.length; i++) {
      if (this.rounds[this.matches[i].round] == null)
        this.rounds[this.matches[i].round] = [];
      this.rounds[this.matches[i].round].push(this.matches[i]);
    }
  }

  setMap() {
    for (let match of this.matches) this.map.set(match.id, match);
  }

  setNumberOfRounds() {
    this.numberOfRounds = this.rounds.length - 1;
  }

  setRoundsStrings() {
    for (let i = 1; i <= this.numberOfRounds; i++) {
      var tmp = 'Round ' + i;
      if (i == this.numberOfRounds) tmp = 'Final';
      else if (i == this.numberOfRounds - 1) tmp = 'Semi-final';
      this.rounds_strings.push(tmp);
    }
  }

  setCurrentRound(round: number) {
    this.currentRound = round;
  }

  setTournamentMatchScores() {
    var requests: MatchScoreRequest[] = [];

    for (let i = 1; i < this.rounds.length; i++) {
      for (let j = 0; j < this.rounds[i].length; j++) {
        if (
          this.refMatches[i].at(j * 2).value != null &&
          this.refMatches[i].at(j * 2 + 1).value != null
        ) {
          var oldScoreFirst = this.map.get(
            this.rounds[i][j].id
          )?.firstCompetitorScore;
          var oldScoreSecond = this.map.get(
            this.rounds[i][j].id
          )?.secondCompetitorScore;
          var newScoreFirst = this.refMatches[i].at(j * 2).value;
          var newScoreSecond = this.refMatches[i].at(j * 2 + 1).value;
          if (
            oldScoreFirst != newScoreFirst ||
            oldScoreSecond != newScoreSecond
          ) {
            requests.push(
              new MatchScoreRequest(
                this.rounds[i][j].id,
                this.refMatches[i].at(j * 2).value,
                this.refMatches[i].at(j * 2 + 1).value
              )
            );
          }
        }
      }
    }

    this.tournamentService.setTournamentMatchScores(requests).subscribe({
      next: () => {
        this.updateMatches();
      },
    });
  }

  get refMatchesr1() {
    return this.matchesForm.get('matchesr1') as FormArray;
  }

  get refMatchesr2() {
    return this.matchesForm.get('matchesr2') as FormArray;
  }

  get refMatchesr3() {
    return this.matchesForm.get('matchesr3') as FormArray;
  }

  get refMatchesr4() {
    return this.matchesForm.get('matchesr4') as FormArray;
  }

  get refMatchesr5() {
    return this.matchesForm.get('matchesr5') as FormArray;
  }

  get refMatchesr6() {
    return this.matchesForm.get('matchesr6') as FormArray;
  }
}
