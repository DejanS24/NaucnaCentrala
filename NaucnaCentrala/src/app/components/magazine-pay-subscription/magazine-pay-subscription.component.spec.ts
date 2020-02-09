import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazinePaySubscriptionComponent } from './magazine-pay-subscription.component';

describe('MagazinePaySubscriptionComponent', () => {
  let component: MagazinePaySubscriptionComponent;
  let fixture: ComponentFixture<MagazinePaySubscriptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazinePaySubscriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazinePaySubscriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
