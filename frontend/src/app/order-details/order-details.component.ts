import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order, OrderControllerService } from 'generated-client';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  order?: Order = undefined;

  constructor(protected orderControllerService: OrderControllerService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.orderControllerService.getOrder(id).subscribe(order => {
      this.order = order;
    });
  }

}
