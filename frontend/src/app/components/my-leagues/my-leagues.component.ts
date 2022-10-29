import { Component, OnInit } from '@angular/core';
import { CompetitionService } from 'src/app/services/competition.service';
import { LeagueService } from 'src/app/services/league.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';

@Component({
  selector: 'app-my-leagues',
  templateUrl: './my-leagues.component.html',
  styleUrls: ['./my-leagues.component.css']
})
export class MyLeaguesComponent implements OnInit {
  ids: any
  names!: string[]

  constructor(private lsService: LocalStorageService, private competitionService: CompetitionService) { }

  ngOnInit(): void {
    this.ids = this.lsService.getAdminOfCompetitions()
    this.competitionService.getCompetitionNames(this.ids).subscribe(
      data => {
        this.names = data
      }
    )
  }
}
