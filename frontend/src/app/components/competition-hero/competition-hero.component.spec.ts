import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionHeroComponent } from './competition-hero.component';

describe('CompetitionHeroComponent', () => {
  let component: CompetitionHeroComponent;
  let fixture: ComponentFixture<CompetitionHeroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompetitionHeroComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompetitionHeroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
