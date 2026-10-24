import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Order, OrderRequest } from '../models/order';
import { AuthService } from './auth.service';

const API_BASE = 'https://blob-spruce-anywhere.ngrok-free.dev/api';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient, private authService: AuthService) {}

  placeOrder(packageId: number, quantity: number, contactPhone: string, contactEmail: string, contactAddress: string, itemDescription: string): Promise<Order | undefined> {
    const payload: OrderRequest = { packageId, quantity, contactPhone, contactEmail, contactAddress, itemDescription };
    return this.http.post<Order>(`${API_BASE}/orders`, payload, { headers: this.authService.authHeaders }).toPromise();
  }

  getMyOrders(): Promise<Order[] | undefined> {
    return this.http.get<Order[]>(`${API_BASE}/orders/me`, { headers: this.authService.authHeaders }).toPromise();
  }
}
