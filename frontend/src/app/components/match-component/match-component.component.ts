import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MatchScoreRequest } from 'src/app/model/dto/request/MatchScoreRequest';
import { MatchResponse } from 'src/app/model/dto/response/MatchResponse';
import { AuthService } from 'src/app/services/auth.service';
import { RRService } from 'src/app/services/roundrobin/rr.service';

@Component({
  selector: 'app-match-component',
  templateUrl: './match-component.component.html',
  styleUrls: ['./match-component.component.css'],
})
export class MatchComponentComponent implements OnInit {
  @Input() match!: MatchResponse;
  @Input() modalId!: string;
  @Output() setMatchScoreEvent = new EventEmitter<string>();
  setMatchScoreForm!: FormGroup;
  isAdminOfCompetition = false;
  competitionId!: number;

  constructor(
    private leagueService: RRService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.getIdFromRoute();
    this.getIsAdminOfCompetition();
  }

  initForm() {
    this.setMatchScoreForm = new FormGroup({
      firstCompetitorScore: new FormControl(this.match.firstCompetitorScore, [
        Validators.required,
        Validators.min(0),
      ]),
      secondCompetitorScore: new FormControl(this.match.secondCompetitorScore, [
        Validators.required,
        Validators.min(0),
      ]),
    });
  }

  getIdFromRoute() {
    this.route.params.subscribe((params) => {
      this.competitionId = +params['id'];
    });
  }

  getIsAdminOfCompetition() {
    this.isAdminOfCompetition = this.authService.isAdminOfCompetition(
      this.competitionId
    );
  }

  setMatchScore() {
    var formData = this.setMatchScoreForm.value;
    var setMatchScoreDto = new MatchScoreRequest(
      this.match.id,
      formData.firstCompetitorScore,
      formData.secondCompetitorScore
    );

    this.leagueService.setMatchScore(setMatchScoreDto).subscribe((data) => {
      this.match = data;
      this.setMatchScoreEvent.emit('some data');
      // update schedule after set match score
      this.leagueService.sendUpdate('update data');
    });
  }
}
