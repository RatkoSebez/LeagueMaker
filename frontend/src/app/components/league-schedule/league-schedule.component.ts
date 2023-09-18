import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatchResponse } from 'src/app/model/dto/response/MatchResponse';
import { CompetitorService } from 'src/app/services/competitor.service';
import { RRService } from 'src/app/services/roundrobin/rr.service';

@Component({
  selector: 'app-league-schedule',
  templateUrl: './league-schedule.component.html',
  styleUrls: ['./league-schedule.component.css'],
})
export class LeagueScheduleComponent implements OnInit {
  competitionId!: number;
  matches!: MatchResponse[];
  numberOfRounds!: number;
  rounds!: number[];
  currentRound = 1;

  constructor(
    private route: ActivatedRoute,
    private leagueService: RRService,
    private competitorService: CompetitorService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe({
      next: (params) => {
        this.competitionId = +params['id'];
        this.leagueService.getNumberOfRounds(this.competitionId).subscribe({
          next: (data) => {
            this.numberOfRounds = data;
            this.rounds = Array.from({ length: data }, (v, k) => k + 1);
            this.setRounds(1);
          },
        });
      },
    });
    this.getScheduleForRound(this.currentRound);

    // subscribe to sender component messages
    this.leagueService.getUpdate().subscribe(() => {
      this.getScheduleForRound(this.currentRound);
    });

    // subscribe competition-hero
    this.competitorService.getUpdate().subscribe(() => {
      this.getScheduleForRound(this.currentRound);
    });
  }

  getScheduleForRound(round: number) {
    this.setRounds(round);
    this.currentRound = round;
    this.leagueService
      .getScheduleForOneRound(this.competitionId, round)
      .subscribe({
        next: (data) => {
          this.matches = data;
        },
      });
  }

  setRounds(current: number) {
    this.rounds = [];
    if (current - 8 > 0) this.rounds.push(current - 8);
    if (current - 4 > 0) this.rounds.push(current - 4);
    if (current - 2 > 0) this.rounds.push(current - 2);
    if (current - 1 > 0) this.rounds.push(current - 1);
    this.rounds.push(current);
    if (current + 1 <= this.numberOfRounds) this.rounds.push(current + 1);
    if (current + 2 <= this.numberOfRounds) this.rounds.push(current + 2);
    if (current + 4 <= this.numberOfRounds) this.rounds.push(current + 4);
    if (current + 8 <= this.numberOfRounds) this.rounds.push(current + 8);
  }
}
