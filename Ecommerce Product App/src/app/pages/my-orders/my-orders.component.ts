import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../../services/order.service';
import { Order } from '../../models/order';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {
  orders: Order[] = [];
  loading = false;
  error: string | null = null;

  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.loading = true;
    this.error = null;
    this.orderService.getMyOrders().then((data: Order[] | undefined) => {
      this.orders = data || [];
    }).catch(err => {
      this.error = err?.error?.error || 'Failed to load orders';
    }).finally(() => {
      this.loading = false;
    });
  }

  goBack(): void {
    this.router.navigate(['/packages']);
  }
}
