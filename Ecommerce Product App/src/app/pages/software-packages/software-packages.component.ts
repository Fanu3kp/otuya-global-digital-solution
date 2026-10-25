import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SoftwarePackageService } from '../../services/software-package.service';

@Component({
  selector: 'app-software-packages',
  templateUrl: './software-packages.component.html',
  styleUrls: ['./software-packages.component.css']
})
export class SoftwarePackagesComponent implements OnInit {
  categories: string[] = ['all'];

  constructor(
    public softwarePackageService: SoftwarePackageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.softwarePackageService.fetchCategories();
    this.softwarePackageService.fetchPackages();
  }

  get isAdmin(): boolean {
    return true;
  }

  onSearchChange(): void {
    this.softwarePackageService.currentPage = 0;
    this.softwarePackageService.fetchPackages();
  }

  onCategoryChange(): void {
    this.softwarePackageService.currentPage = 0;
    this.softwarePackageService.fetchPackages();
  }

  onSortChange(): void {
    this.softwarePackageService.fetchPackages();
  }

  goToAddPackage(): void {
    this.router.navigate(['/packages/new']);
  }

  goToMyOrders(): void {
    this.router.navigate(['/my-orders']);
  }
}
