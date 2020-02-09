import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciWorkReviewComponent } from './sci-work-review.component';

describe('SciWorkReviewComponent', () => {
  let component: SciWorkReviewComponent;
  let fixture: ComponentFixture<SciWorkReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciWorkReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciWorkReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
