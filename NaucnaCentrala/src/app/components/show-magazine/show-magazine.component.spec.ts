import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowMagazineComponent } from './show-magazine.component';

describe('ShowMagazineComponent', () => {
  let component: ShowMagazineComponent;
  let fixture: ComponentFixture<ShowMagazineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowMagazineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
