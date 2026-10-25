import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SoftwarePackageCardComponent } from './components/software-package-card/software-package-card.component';
import { SoftwarePackagesComponent } from './pages/software-packages/software-packages.component';
import { AddSoftwarePackageComponent } from './pages/add-software-package/add-software-package.component';
import { ViewSoftwarePackageComponent } from './pages/view-software-package/view-software-package.component';
import { OrderComponent } from './pages/order/order.component';
import { MyOrdersComponent } from './pages/my-orders/my-orders.component';

@NgModule({
  declarations: [
    AppComponent,
    SoftwarePackageCardComponent,
    SoftwarePackagesComponent,
    AddSoftwarePackageComponent,
    ViewSoftwarePackageComponent,
    OrderComponent,
    MyOrdersComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
