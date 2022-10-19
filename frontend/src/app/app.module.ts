import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { BonnieHeaderComponent } from './bonnie-header/bonnie-header.component';
import { OrderTableComponent } from './order-table/order-table.component';
import { AllOrdersComponent } from './all-orders/all-orders.component'; 
import { LoginPageComponent } from './login-page/login-page.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { UnassignedOrdersComponent } from './unassigned-orders/unassigned-orders.component';
import { UsersComponent } from './users/users.component';
import { OrderControllerService, UserControllerService } from 'generated-client';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { BASE_PATH } from 'generated-client';
import { FormsModule } from '@angular/forms';
import { SecurityInterceptor } from './security-interceptor';
import { UserService } from './userService';

@NgModule({
  declarations: [
    AppComponent,
    AllOrdersComponent,
    BonnieHeaderComponent,
    LoginPageComponent,
    MyOrdersComponent,
    UnassignedOrdersComponent,
    UsersComponent,
    OrderDetailsComponent,
    LoginPageComponent,
    OrderTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    { provide: BASE_PATH, useValue: "http://localhost:8082" },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: SecurityInterceptor,
      multi: true,
    },
    OrderControllerService,
    UserControllerService,
    UserService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
