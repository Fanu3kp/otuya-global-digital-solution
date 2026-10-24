import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { SoftwarePackageService } from '../../services/software-package.service';
import { SoftwarePackageCardComponent } from '../../components/software-package-card/software-package-card.component';
import { SoftwarePackage } from '../../models/software-package';

@Component({
  selector: 'app-software-packages',
  templateUrl: './software-packages.component.html',
  styleUrls: ['./software-packages.component.css']
})
export class SoftwarePackagesComponent implements OnInit {
  searchQuery = '';
  selectedCategory = 'all';
  sortBy = 'title';
  sortOrder = 'asc';
  isMobileMenuOpen = false;
  private searchTimeout: any;

  constructor(
    public router: Router,
    public softwarePackageService: SoftwarePackageService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    this.auth.restoreSession();
    this.softwarePackageService.fetchCategories();
    this.softwarePackageService.fetchPackages();
  }

  get isAdmin(): boolean {
    return this.auth.isAdmin;
  }

  get rangeStart(): number {
    return this.softwarePackageService.currentPage * this.softwarePackageService.itemsPerPage + 1;
  }

  get rangeEnd(): number {
    return Math.min((this.softwarePackageService.currentPage + 1) * this.softwarePackageService.itemsPerPage, this.softwarePackageService.total);
  }

  get visiblePages(): (number | string)[] {
    const total = this.softwarePackageService.totalPages;
    const current = this.softwarePackageService.currentPage;
    const pages: (number | string)[] = [];

    if (total <= 7) {
      for (let i = 1; i <= total; i++) {
        pages.push(i);
      }
    } else {
      pages.push(1);

      if (current > 3) {
        pages.push('...');
      }

      for (let i = Math.max(2, current - 1); i <= Math.min(total - 1, current + 1); i++) {
        pages.push(i);
      }

      if (current < total - 2) {
        pages.push('...');
      }

      pages.push(total);
    }

    return pages;
  }

  handleSearch(): void {
    clearTimeout(this.searchTimeout);
    this.searchTimeout = setTimeout(() => {
      this.softwarePackageService.setSearchQuery(this.searchQuery);
      this.softwarePackageService.fetchPackages();
    }, 500);
  }

  handleCategoryChange(): void {
    this.softwarePackageService.setCategory(this.selectedCategory);
    this.softwarePackageService.fetchPackages();
  }

  handleSortChange(): void {
    this.softwarePackageService.setSorting(this.sortBy, this.sortOrder);
    this.softwarePackageService.fetchPackages();
  }

  goToPage(page: number | string): void {
    if (page === '...' || typeof page !== 'number' || page < 1 || page > this.softwarePackageService.totalPages) {
      return;
    }
    this.softwarePackageService.setPage(page);
    this.softwarePackageService.fetchPackages();
  }

  viewPackage(pkg: SoftwarePackage): void {
    this.router.navigate(['/packages', pkg.id]);
  }

  goToAddPackage(): void {
    this.router.navigate(['/packages', 'new']);
  }

  formatCategory(category: string): string {
    return category
      .split('-')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }

  toggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }
}
