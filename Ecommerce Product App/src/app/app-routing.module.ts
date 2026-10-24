import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SoftwarePackagesComponent } from './pages/software-packages/software-packages.component';
import { AddSoftwarePackageComponent } from './pages/add-software-package/add-software-package.component';
import { ViewSoftwarePackageComponent } from './pages/view-software-package/view-software-package.component';
import { OrderComponent } from './pages/order/order.component';
import { MyOrdersComponent } from './pages/my-orders/my-orders.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'packages', component: SoftwarePackagesComponent },
  { path: 'packages/new', component: AddSoftwarePackageComponent },
  { path: 'packages/:id', component: ViewSoftwarePackageComponent },
  { path: 'packages/:id/order', component: OrderComponent },
  { path: 'my-orders', component: MyOrdersComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
