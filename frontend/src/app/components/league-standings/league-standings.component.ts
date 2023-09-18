import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompetitorResponse } from 'src/app/model/dto/response/CompetitorResponse';
import { CompetitorService } from 'src/app/services/competitor.service';
import { RRService } from 'src/app/services/roundrobin/rr.service';

@Component({
  selector: 'app-league-standings',
  templateUrl: './league-standings.component.html',
  styleUrls: ['./league-standings.component.css'],
})
export class LeagueStandingsComponent implements OnInit {
  competitionId!: number;
  standings!: CompetitorResponse[];
  competitorId!: number;

  constructor(
    private route: ActivatedRoute,
    private leagueService: RRService,
    private competitorService: CompetitorService
  ) {}

  ngOnInit(): void {
    this.getIdFromRoute();

    // subscribe to sender component messages
    this.leagueService.getUpdate().subscribe(() => {
      this.updateStandings();
    });

    // subscribe competition-hero
    this.competitorService.getUpdate().subscribe(() => {
      this.updateStandings();
    });
  }

  getIdFromRoute() {
    this.route.params.subscribe((params) => {
      this.competitionId = +params['id'];
      this.updateStandings();
    });
  }

  public updateStandings() {
    this.leagueService.getStandings(this.competitionId).subscribe((data) => {
      this.standings = data;
    });
  }
}
