import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyLeaguesComponent } from './my-leagues.component';

describe('MyLeaguesComponent', () => {
  let component: MyLeaguesComponent;
  let fixture: ComponentFixture<MyLeaguesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyLeaguesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyLeaguesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
