import { Component, Input, OnInit } from '@angular/core';
import { Match } from 'src/app/model/Match';

@Component({
  selector: 'app-match-component',
  templateUrl: './match-component.component.html',
  styleUrls: ['./match-component.component.css']
})
export class MatchComponentComponent implements OnInit {
  @Input() match!: Match;

  constructor() { }

  ngOnInit(): void {
  }

}
