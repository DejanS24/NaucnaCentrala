import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciWorkMagazineChoiceComponent } from './sci-work-magazine-choice.component';

describe('SciWorkMagazineChoiceComponent', () => {
  let component: SciWorkMagazineChoiceComponent;
  let fixture: ComponentFixture<SciWorkMagazineChoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciWorkMagazineChoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciWorkMagazineChoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
