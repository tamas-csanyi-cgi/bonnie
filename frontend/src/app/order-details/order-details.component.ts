import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";

import { TrackingNumberComponent } from '../common/tracking-number/tracking-number.component';
import { Order, OrderControllerService } from 'generated-client';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  order?: Order = undefined;

  constructor(protected orderControllerService: OrderControllerService,
    private dialog: MatDialog,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.orderControllerService.getOrder(id).subscribe(order => {
      this.order = order;
    });
  }

  openTrackingNr(order: Order): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = order;

    this.dialog.open(TrackingNumberComponent, dialogConfig);
  }

}
