import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RRService } from 'src/app/services/roundrobin/rr.service';

@Component({
  selector: 'app-league-details-page',
  templateUrl: './league-details-page.component.html',
  styleUrls: ['./league-details-page.component.css']
})
export class LeagueDetailsPageComponent implements OnInit {
  

  constructor() { }

  ngOnInit(): void {
    
  }

}
