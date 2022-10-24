import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { TrackingNumberComponent } from '../common/tracking-number/tracking-number.component';
import { Order } from 'generated-client';

@Component({
  selector: 'order-table',
  templateUrl: './order-table.component.html',
  styleUrls: ['./order-table.component.css']
})
export class OrderTableComponent implements OnInit {

  @Input() orders: Order[] = [];

  displayedColumns: string[] = ['id', 'quantity', 'status', 'assignedTo', 'trackingNr' ];

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void { }

  openTrackingNr(order: Order): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = order;

    this.dialog.open(TrackingNumberComponent, dialogConfig);
  }

}
