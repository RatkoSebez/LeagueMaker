import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitorInfoComponent } from './competitor-info.component';

describe('CompetitorInfoComponent', () => {
  let component: CompetitorInfoComponent;
  let fixture: ComponentFixture<CompetitorInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompetitorInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompetitorInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
