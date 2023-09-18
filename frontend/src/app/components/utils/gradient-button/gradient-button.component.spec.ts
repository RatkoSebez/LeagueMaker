import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradientButtonComponent } from './gradient-button.component';

describe('GradientButtonComponent', () => {
  let component: GradientButtonComponent;
  let fixture: ComponentFixture<GradientButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GradientButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GradientButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
