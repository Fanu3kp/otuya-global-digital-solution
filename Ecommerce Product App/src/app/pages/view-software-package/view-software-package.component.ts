import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { SoftwarePackageService } from '../../services/software-package.service';
import { SoftwarePackage } from '../../models/software-package';

@Component({
  selector: 'app-view-software-package',
  templateUrl: './view-software-package.component.html',
  styleUrls: ['./view-software-package.component.css']
})
export class ViewSoftwarePackageComponent implements OnInit {
  package: SoftwarePackage | null = null;
  loading = false;
  error: string | null = null;
  deleting = false;
  currentImage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private softwarePackageService: SoftwarePackageService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    this.auth.restoreSession();
    this.fetchPackage();
  }

  get isAdmin(): boolean {
    return this.auth.isAdmin;
  }

  async fetchPackage(): Promise<void> {
    this.loading = true;
    this.error = null;
    try {
      const id = Number(this.route.snapshot.paramMap.get('id'));
      const data = await this.softwarePackageService.fetchPackageById(id);
      if (data) {
        this.package = data;
        this.currentImage = data.thumbnail || (data.images && data.images[0]) || 'https://via.placeholder.com/600';
      } else {
        this.error = 'Software package not found';
      }
    } catch (err) {
      this.error = 'Failed to load package';
      console.error(err);
    } finally {
      this.loading = false;
    }
  }

  async handleDelete(): Promise<void> {
    if (!confirm('Are you sure you want to delete this software package?')) {
      return;
    }

    this.deleting = true;
    const id = this.package!.id;
    const success = await this.softwarePackageService.deletePackage(id);
    this.deleting = false;

    if (success) {
      this.router.navigate(['/packages']);
    } else {
      alert('Failed to delete software package');
    }
  }

  goBack(): void {
    this.router.navigate(['/packages']);
  }
}
