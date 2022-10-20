import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Order, OrderControllerService } from 'generated-client';

const snackBarConfig = {
  duration: 5 * 1000
}

@Component({
  selector: 'tracking-number',
  templateUrl: './tracking-number.component.html',
  styleUrls: ['./tracking-number.component.css']
})
export class TrackingNumberComponent implements OnInit {

  order: Order;

  originalTrackingNr? : String;

  constructor(public dialogRef: MatDialogRef<TrackingNumberComponent>,
    private _snackBar: MatSnackBar,
    private orderControllerService: OrderControllerService,
    @Inject(MAT_DIALOG_DATA) public data: Order) {
      this.order = data;
      this.originalTrackingNr = data.trackingNr;
    }

  ngOnInit(): void { }

  saveTrackingNr() {
    this.orderControllerService.shipOrder(this.order.id!, this.order.trackingNr!, 'response').subscribe(respo => {
      this._snackBar.open("Tracking number saved!", '', snackBarConfig);
      this.dialogRef.close();
    }, err => {
      this._snackBar.open("Error during settings tracking number!", 'OK', snackBarConfig);
      console.error(err);
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
