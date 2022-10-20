import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackingNumberComponent } from './tracking-number.component';

describe('TrackingNumberComponent', () => {
  let component: TrackingNumberComponent;
  let fixture: ComponentFixture<TrackingNumberComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TrackingNumberComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrackingNumberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
