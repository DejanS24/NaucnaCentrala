import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciWorkEditorReviewComponent } from './sci-work-editor-review.component';

describe('SciWorkEditorReviewComponent', () => {
  let component: SciWorkEditorReviewComponent;
  let fixture: ComponentFixture<SciWorkEditorReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciWorkEditorReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciWorkEditorReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
