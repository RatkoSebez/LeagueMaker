import { Component, OnInit, Input } from '@angular/core';
import { CompetitionResponse } from 'src/app/model/dto/response/CompetitionResponse';

@Component({
  selector: 'app-competition-card',
  templateUrl: './competition-card.component.html',
  styleUrls: ['./competition-card.component.css'],
})
export class CompetitionCardComponent implements OnInit {
  @Input() competition!: CompetitionResponse;

  constructor() {}

  ngOnInit(): void {}
}
