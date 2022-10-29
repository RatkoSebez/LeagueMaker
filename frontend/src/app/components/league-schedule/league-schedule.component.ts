import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Match } from 'src/app/model/Match';
import { CompetitionService } from 'src/app/services/competition.service';
import { LeagueService } from 'src/app/services/league.service';

@Component({
  selector: 'app-league-schedule',
  templateUrl: './league-schedule.component.html',
  styleUrls: ['./league-schedule.component.css']
})
export class LeagueScheduleComponent implements OnInit {
  id!: number;
  sub: any;
  matches!: Match[]
  numberOfRounds!: number
  rounds!: number[]
  currentRound = 1

  constructor(private route: ActivatedRoute, private leagueService: LeagueService, private competitionService: CompetitionService) { }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id']; // (+) converts string 'id' to a number
      this.competitionService.getNumberOfRounds(this.id).subscribe(
        data => {
          this.numberOfRounds = data

          this.rounds = Array.from({length:data},(v,k)=>k+1);
          
          this.setRounds(1)

          // this.numberOfRounds = data
        }
      )
    });
    this.getScheduleForRound(1);
  }

  getScheduleForRound(round: number){
    this.setRounds(round)
    this.currentRound = round
    this.leagueService.getScheduleForOneRound(this.id, round).subscribe(
      data => {
        this.matches = data
        // console.log(data)
      }
    )
  }

  setRounds(current: number){
    this.rounds = []
    if(current - 8 > 0) this.rounds.push(current - 8)
    if(current - 4 > 0) this.rounds.push(current - 4)
    if(current - 2 > 0) this.rounds.push(current - 2)
    if(current - 1 > 0) this.rounds.push(current - 1)
    this.rounds.push(current)
    if(current + 1 <= this.numberOfRounds) this.rounds.push(current + 1)
    if(current + 2 <= this.numberOfRounds) this.rounds.push(current + 2)
    if(current + 4 <= this.numberOfRounds) this.rounds.push(current + 4)
    if(current + 8 <= this.numberOfRounds) this.rounds.push(current + 8)
  }
}
