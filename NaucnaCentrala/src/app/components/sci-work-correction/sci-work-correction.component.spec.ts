import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciWorkCorrectionComponent } from './sci-work-correction.component';

describe('SciWorkCorrectionComponent', () => {
  let component: SciWorkCorrectionComponent;
  let fixture: ComponentFixture<SciWorkCorrectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciWorkCorrectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciWorkCorrectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
