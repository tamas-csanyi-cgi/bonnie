import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Order, OrderControllerService } from 'generated-client';

@Component({
  selector: 'tracking-number',
  templateUrl: './tracking-number.component.html',
  styleUrls: ['./tracking-number.component.css']
})
export class TrackingNumberComponent implements OnInit {

  order: Order;

  orderControllerService: OrderControllerService;

  constructor(public dialogRef: MatDialogRef<TrackingNumberComponent>,
    private orderControllerServiceProp: OrderControllerService,
    @Inject(MAT_DIALOG_DATA) public data: Order) {
      this.order = data;
      this.orderControllerService = orderControllerServiceProp;
    }

  ngOnInit(): void { }

  saveTrackingNr() {
    console.log(this.order);
    this.orderControllerService.shipOrder(this.order.id!, this.order.trackingNr!).subscribe(respo => {
      console.log(respo);
    });
    //this.dialogRef.close();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
