import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazineCreationComponent } from './magazine-creation.component';

describe('MagazineComponent', () => {
  let component: MagazineCreationComponent;
  let fixture: ComponentFixture<MagazineCreationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazineCreationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazineCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
