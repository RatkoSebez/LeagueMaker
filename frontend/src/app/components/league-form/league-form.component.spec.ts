import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeagueFormComponent } from './league-form.component';

describe('LeagueFormComponent', () => {
  let component: LeagueFormComponent;
  let fixture: ComponentFixture<LeagueFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeagueFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeagueFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
