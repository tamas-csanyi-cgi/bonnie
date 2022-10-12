import { Component, Input, OnInit } from '@angular/core';
import { Order } from 'generated-client';

@Component({
  selector: 'order-table',
  templateUrl: './order-table.component.html',
  styleUrls: ['./order-table.component.css']
})
export class OrderTableComponent implements OnInit {

  @Input() orders: Order[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
