import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchComponentComponent } from './match-component.component';

describe('MatchComponentComponent', () => {
  let component: MatchComponentComponent;
  let fixture: ComponentFixture<MatchComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MatchComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MatchComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
