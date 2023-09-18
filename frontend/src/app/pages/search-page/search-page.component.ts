import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompetitionResponse } from 'src/app/model/dto/response/CompetitionResponse';
import { CompetitionService } from 'src/app/services/competition.service';

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css'],
})
export class SearchPageComponent implements OnInit {
  query = '';
  competitions!: CompetitionResponse[];

  constructor(
    private competitionService: CompetitionService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.query = params['query'];
    });

    this.competitionService.getSearchResults(this.query).subscribe((data) => {
      this.competitions = data;
    });
  }
}
