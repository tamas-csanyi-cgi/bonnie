import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { UnassignedOrdersComponent } from './unassigned-orders/unassigned-orders.component';

const routes: Routes = [
  { path: 'my-orders', component: MyOrdersComponent },
  { path: 'order/:id', component: OrderDetailsComponent },
  { path: 'unassigned-orders', component: UnassignedOrdersComponent },
  { path: 'login-form', component: LoginPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
