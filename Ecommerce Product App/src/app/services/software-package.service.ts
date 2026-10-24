import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SoftwarePackage } from '../models/software-package';
import { AuthService } from './auth.service';

interface SoftwarePackageResponse {
  id: number;
  title: string;
  description: string;
  price: number;
  stock: number;
  category: string;
  brand?: string;
  thumbnail?: string;
  images?: string[];
  rating?: number;
  discountPercentage?: number;
}

interface PackagesPage {
  content: SoftwarePackageResponse[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

@Injectable({
  providedIn: 'root'
})
export class SoftwarePackageService {
  packages: SoftwarePackage[] = [];
  categories: string[] = ['all'];
  isLoading = false;
  error: string | null = null;
  searchQuery = '';
  selectedCategory = 'all';
  sortBy = 'title';
  sortOrder = 'asc';
  currentPage = 0;
  itemsPerPage = 12;
  total = 0;

  get totalPages(): number {
    return Math.ceil(this.total / this.itemsPerPage);
  }

  constructor(private http: HttpClient, private authService: AuthService) {}

  async fetchCategories(): Promise<void> {
    try {
      const res: any = await this.http.get(`${API_BASE}/packages/categories`).toPromise();
      const cats = res?.categories || ['all'];
      this.categories = cats;
    } catch (err) {
      console.error('fetchCategories error:', err);
      this.categories = ['all'];
    }
  }

  async fetchPackages(): Promise<void> {
    this.isLoading = true;
    this.error = null;
    try {
      const params: any = {
        page: this.currentPage,
        size: this.itemsPerPage
      };
      if (this.searchQuery) params.search = this.searchQuery;
      if (this.selectedCategory && this.selectedCategory !== 'all') params.category = this.selectedCategory;

      const res = await this.http.get<PackagesPage>(`${API_BASE}/packages`, { params }).toPromise();

      this.packages = (res?.content || []).map((p: any) => ({
        id: p.id,
        title: p.title,
        description: p.description,
        price: p.price,
        stock: p.stock,
        category: p.category,
        brand: p.brand,
        thumbnail: p.thumbnail,
        images: p.images,
        rating: p.rating,
        discountPercentage: p.discountPercentage
      }));
      this.total = res?.totalElements || 0;
    } catch (err: any) {
      this.error = err.error?.error || 'Failed to load packages';
      console.error('fetchPackages error:', err);
    } finally {
      this.isLoading = false;
    }
  }

  async fetchPackageById(id: number): Promise<SoftwarePackage | null> {
    this.isLoading = true;
    this.error = null;
    try {
      const res = await this.http.get<SoftwarePackageResponse>(`${API_BASE}/packages/${id}`).toPromise();
      return res ? {
        id: res.id,
        title: res.title,
        description: res.description,
        price: res.price,
        stock: res.stock,
        category: res.category,
        brand: res.brand,
        thumbnail: res.thumbnail,
        images: res.images,
        rating: res.rating,
        discountPercentage: res.discountPercentage
      } : null;
    } catch (err: any) {
      this.error = err.error?.error || 'Failed to fetch package';
      console.error('fetchPackageById error:', err);
      return null;
    } finally {
      this.isLoading = false;
    }
  }

  async addPackage(payload: any): Promise<SoftwarePackage | null> {
    this.isLoading = true;
    this.error = null;
    try {
      const res = await this.http.post<SoftwarePackageResponse>(`${API_BASE}/packages`, payload, { headers: this.authService.authHeaders }).toPromise();
      await this.fetchPackages();
      return res ? {
        id: res.id,
        title: res.title,
        description: res.description,
        price: res.price,
        stock: res.stock,
        category: res.category,
        brand: res.brand,
        thumbnail: res.thumbnail,
        images: res.images,
        rating: res.rating,
        discountPercentage: res.discountPercentage
      } : null;
    } catch (err: any) {
      this.error = err.error?.error || 'Failed to add package';
      console.error('addPackage error:', err);
      return null;
    } finally {
      this.isLoading = false;
    }
  }

  async uploadImage(formData: FormData): Promise<any> {
    this.isLoading = true;
    this.error = null;
    try {
      const res = await this.http.post(`${API_BASE}/uploads/product-image`, formData, { headers: this.authService.authHeaders }).toPromise();
      return res;
    } catch (err: any) {
      this.error = err.error?.error || 'Failed to upload image';
      console.error('uploadImage error:', err);
      throw err;
    } finally {
      this.isLoading = false;
    }
  }

  async deletePackage(id: number): Promise<boolean> {
    this.isLoading = true;
    this.error = null;
    try {
      await this.http.delete(`${API_BASE}/packages/${id}`, { headers: this.authService.authHeaders }).toPromise();
      this.packages = this.packages.filter(p => p.id !== id);
      this.total = Math.max(0, this.total - 1);
      return true;
    } catch (err: any) {
      this.error = err.error?.error || 'Failed to delete package';
      console.error('deletePackage error:', err);
      return false;
    } finally {
      this.isLoading = false;
    }
  }

  setSearchQuery(query: string): void {
    this.searchQuery = query;
    this.currentPage = 0;
  }

  setCategory(category: string): void {
    this.selectedCategory = category;
    this.currentPage = 0;
  }

  setSorting(sortBy: string, sortOrder = 'asc'): void {
    this.sortBy = sortBy;
    this.sortOrder = sortOrder;
    this.currentPage = 0;
  }

  setPage(page: number): void {
    this.currentPage = page;
  }
}

const API_BASE = 'https://blob-spruce-anywhere.ngrok-free.dev/api';
