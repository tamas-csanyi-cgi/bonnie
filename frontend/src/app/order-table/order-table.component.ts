import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { TrackingNumberComponent } from '../common/tracking-number/tracking-number.component';
import { Order, OrderControllerService } from 'generated-client';

@Component({
  selector: 'order-table',
  templateUrl: './order-table.component.html',
  styleUrls: ['./order-table.component.css']
})
export class OrderTableComponent implements OnInit {

  @Input() orders: Order[] = [];

  constructor(private dialog: MatDialog, protected orderControllerService: OrderControllerService) {}

  ngOnInit(): void {
  }

  openTrackingNr(order: Order): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = order;

    this.dialog.open(TrackingNumberComponent, dialogConfig);
  }

  releaseOrder(order : number): void{ 
    this.orderControllerService.releaseOrder(order).subscribe();
    window.location.reload();
  }

  claimOrder(order: number):void{
    this.orderControllerService.assignToMe(order).subscribe();
    window.location.reload();
  }

}
