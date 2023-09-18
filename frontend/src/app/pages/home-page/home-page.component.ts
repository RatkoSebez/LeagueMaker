import { Component, OnInit } from '@angular/core';
import { CompetitionResponse } from 'src/app/model/dto/response/CompetitionResponse';
import { AuthService } from 'src/app/services/auth.service';
import { CompetitionService } from 'src/app/services/competition.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
})
export class HomePageComponent implements OnInit {
  loggedIn = false;
  competitions!: CompetitionResponse[];

  constructor(
    private authService: AuthService,
    private localStorageService: LocalStorageService,
    private competitionService: CompetitionService
  ) {}

  ngOnInit(): void {
    this.loggedIn = this.authService.getIsLoggedIn();
    this.getCompetitions();
  }

  scroll(el: HTMLElement) {
    el.scrollIntoView();
  }

  getCompetitions() {
    var ids = this.localStorageService.getAdminOfCompetitions();
    if (ids.length == 0) return;
    this.competitionService.getCompetitionNames(ids).subscribe({
      next: (data) => {
        this.competitions = data;
      },
    });
  }
}
