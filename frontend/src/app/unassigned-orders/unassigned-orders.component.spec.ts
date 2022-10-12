import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnassignedOrdersComponent } from './unassigned-orders.component';

describe('UnassignedOrdersComponent', () => {
  let component: UnassignedOrdersComponent;
  let fixture: ComponentFixture<UnassignedOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnassignedOrdersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnassignedOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
