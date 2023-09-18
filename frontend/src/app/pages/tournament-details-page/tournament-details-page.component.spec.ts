import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentDetailsPageComponent } from './tournament-details-page.component';

describe('TournamentDetailsPageComponent', () => {
  let component: TournamentDetailsPageComponent;
  let fixture: ComponentFixture<TournamentDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TournamentDetailsPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TournamentDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
