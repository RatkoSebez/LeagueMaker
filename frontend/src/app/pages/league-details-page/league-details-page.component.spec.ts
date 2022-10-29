import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeagueDetailsPageComponent } from './league-details-page.component';

describe('LeagueDetailsPageComponent', () => {
  let component: LeagueDetailsPageComponent;
  let fixture: ComponentFixture<LeagueDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeagueDetailsPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeagueDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
