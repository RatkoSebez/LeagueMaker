import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleEliminationTournamentFormComponent } from './single-elimination-tournament-form.component';

describe('SingleEliminationTournamentFormComponent', () => {
  let component: SingleEliminationTournamentFormComponent;
  let fixture: ComponentFixture<SingleEliminationTournamentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleEliminationTournamentFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SingleEliminationTournamentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
