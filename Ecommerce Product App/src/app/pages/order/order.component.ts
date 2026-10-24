import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SoftwarePackageService } from '../../services/software-package.service';
import { OrderService } from '../../services/order.service';
import { SoftwarePackage } from '../../models/software-package';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  package: SoftwarePackage | null = null;
  loading = false;
  quantity = 1;
  contactPhone = '';
  contactEmail = '';
  contactAddress = '';
  itemDescription = '';
  placing = false;
  currentImage = '';
  successMessage = '';
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private softwarePackageService: SoftwarePackageService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.fetchPackage(id);
  }

  fetchPackage(id: number): void {
    this.loading = true;
    this.errorMessage = '';
    this.softwarePackageService.fetchPackageById(id).then((data: SoftwarePackage | null) => {
      this.package = data;
      if (!data) {
        this.errorMessage = 'Software package not found';
      }
    }).finally(() => {
      this.loading = false;
    });
  }

  async placeOrder(): Promise<void> {
    if (!this.package) return;
    if (!this.contactPhone || !this.contactEmail || !this.contactAddress) {
      this.errorMessage = 'Please fill in all contact details (phone, email, address)';
      return;
    }
    this.placing = true;
    this.successMessage = '';
    this.errorMessage = '';
    try {
      await this.orderService.placeOrder(this.package.id, this.quantity, this.contactPhone, this.contactEmail, this.contactAddress, this.itemDescription);
      this.successMessage = 'Order placed successfully! A confirmation has been sent.';
      setTimeout(() => {
        this.router.navigate(['/my-orders']);
      }, 1500);
    } catch (err: any) {
      this.errorMessage = err.error?.error || 'Failed to place order. Please try again.';
    } finally {
      this.placing = false;
    }
  }

  goBack(): void {
    this.router.navigate(['/packages']);
  }
}
