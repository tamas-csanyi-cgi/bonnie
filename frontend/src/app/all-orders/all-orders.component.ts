import { Component, OnInit } from '@angular/core';
import { Order, OrderControllerService } from 'generated-client';

@Component({
  selector: 'app-all-orders',
  templateUrl: './all-orders.component.html',
  styleUrls: ['./all-orders.component.css']
})
export class AllOrdersComponent implements OnInit {

  orders: Order[] = [];

  constructor(protected orderControllerService: OrderControllerService) { }

  ngOnInit(): void {
    this.orderControllerService.configuration.withCredentials = true;
    //this.orderControllerService.defaultHeaders = this.orderControllerService.defaultHeaders.set('Cookie', ['JSESSIONID=9F0851AAD3BBDFB023235E4035288E6C']);
    //this.orderControllerService.defaultHeaders = this.orderControllerService.defaultHeaders.set('alma', ['JSESSIONID=9F0851AAD3BBDFB023235E4035288E6C']);
    this.orderControllerService.getAllOrders().subscribe(allOrders => {
      this.orders = allOrders;
    });
  }

}
