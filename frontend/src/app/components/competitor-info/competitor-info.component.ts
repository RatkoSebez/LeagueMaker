import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompetitorResponse } from 'src/app/model/dto/response/CompetitorResponse';
import { CompetitorService } from 'src/app/services/competitor.service';

@Component({
  selector: 'app-competitor-info',
  templateUrl: './competitor-info.component.html',
  styleUrls: ['./competitor-info.component.css'],
})
export class CompetitorInfoComponent implements OnInit {
  competitorId!: number;
  competitor = new CompetitorResponse();

  constructor(
    private route: ActivatedRoute,
    private competitorService: CompetitorService
  ) {}

  ngOnInit(): void {
    this.getIdFromRoute();
  }

  getIdFromRoute() {
    this.route.params.subscribe((params) => {
      this.competitorId = +params['id'];
      this.getCompetitor();
    });
  }

  getCompetitor() {
    this.competitorService.getCompetitor(this.competitorId).subscribe({
      next: (data) => {
        this.competitor = data;
      },
    });
  }
}
