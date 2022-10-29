import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompetitorDto } from 'src/app/model/dto/CompetitorDto';
import { LeagueStandings } from 'src/app/model/LeagueStandings';
import { AuthService } from 'src/app/services/auth.service';
import { CompetitorService } from 'src/app/services/competitor.service';
import { LeagueService } from 'src/app/services/league.service';

@Component({
  selector: 'app-league-standings',
  templateUrl: './league-standings.component.html',
  styleUrls: ['./league-standings.component.css']
})
export class LeagueStandingsComponent implements OnInit {
  competitionId!: number;
  sub: any;
  standings!: LeagueStandings[]
  roleUser = false
  roleAdmin = false
  loggedIn = false
  isAdminOfCompetition = false;
  competitorName = ""
  competitorId!: number

  constructor(private route: ActivatedRoute, private leagueService: LeagueService, private authService: AuthService, private competitorService: CompetitorService) { }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.competitionId = +params['id']; // (+) converts string 'id' to a number
      this.leagueService.getStandings(this.competitionId).subscribe(
        data => {
          this.standings = data
        }
      )
    });

    this.loggedIn = this.authService.getIsLoggedIn()
    this.roleUser = this.authService.getIsUser()
    this.roleAdmin = this.authService.getIsAdmin()
    this.isAdminOfCompetition = this.authService.isAdminOfCompetition(this.competitionId)
  }

  setCurrentCompetitorData(competitorStandings: LeagueStandings){
    this.competitorName = competitorStandings.competitorName
    this.competitorId = competitorStandings.competitorId
  }

  editCompetitorName(){
    // TODO na beku napravi api za edit competitora
    var competitorDto = new CompetitorDto(this.competitorId, this.competitorName);
    this.competitorService.editName(competitorDto).subscribe(
      data => {
        for(let i=0; i<this.standings.length; i++){
          if(this.standings[i].competitorId === this.competitorId)
            this.standings[i].competitorName = this.competitorName;
        }
      }
    )
  }
}
