import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciWorkCreationComponent } from './sci-work-creation.component';

describe('SciWorkCreationComponent', () => {
  let component: SciWorkCreationComponent;
  let fixture: ComponentFixture<SciWorkCreationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciWorkCreationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciWorkCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
