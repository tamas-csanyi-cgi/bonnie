import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule  } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BonnieHeaderComponent } from './bonnie-header/bonnie-header.component';
import { OrderTableComponent } from './order-table/order-table.component';
import { AllOrdersComponent } from './all-orders/all-orders.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { UnassignedOrdersComponent } from './unassigned-orders/unassigned-orders.component';
import { UsersComponent } from './users/users.component';
import { OrderControllerService, UserControllerService } from 'generated-client';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { TrackingNumberComponent } from './common/tracking-number/tracking-number.component';
import { BASE_PATH } from 'generated-client';

@NgModule({
  declarations: [
    AppComponent,
    BonnieHeaderComponent,
    OrderTableComponent,
    AllOrdersComponent,
    MyOrdersComponent,
    UnassignedOrdersComponent,
    UsersComponent,
    OrderDetailsComponent,
    TrackingNumberComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    MatTableModule
  ],
  providers: [
    { provide: BASE_PATH, useValue: "http://localhost:8082" },
    OrderControllerService,
    UserControllerService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
