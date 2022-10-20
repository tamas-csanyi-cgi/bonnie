import { Component, OnInit } from '@angular/core';
import { Order, OrderControllerService } from 'generated-client';

@Component({
  selector: 'app-unassigned-orders',
  templateUrl: './unassigned-orders.component.html',
  styleUrls: ['./unassigned-orders.component.css']
})
export class UnassignedOrdersComponent implements OnInit {

  orders: Order[] = [];

  constructor(protected orderControllerService: OrderControllerService) { }

  ngOnInit(): void {
    this.orderControllerService.findAllNew().subscribe(unclaimedOrders => {
      this.orders = unclaimedOrders;
    });
  }

}
