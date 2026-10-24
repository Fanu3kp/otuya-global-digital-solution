import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { SoftwarePackageService } from '../../services/software-package.service';

@Component({
  selector: 'app-add-software-package',
  templateUrl: './add-software-package.component.html',
  styleUrls: ['./add-software-package.component.css']
})
export class AddSoftwarePackageComponent implements OnInit {
  formData = {
    title: '',
    description: '',
    price: '',
    stock: '',
    category: '',
    brand: '',
    discountPercentage: '',
    thumbnail: ''
  };
  selectedFile: File | null = null;
  imagePreview: string | null = null;
  isUploading = false;
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';

  constructor(private router: Router, private softwarePackageService: SoftwarePackageService, public auth: AuthService) {}

  ngOnInit(): void {
    this.auth.restoreSession();
    if (!this.auth.isAdmin) {
      this.router.navigate(['/packages']);
    }
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  removeImage(): void {
    this.selectedFile = null;
    this.imagePreview = null;
    this.formData.thumbnail = '';
  }

  async uploadImage(): Promise<string | null> {
    if (!this.selectedFile) return null;

    this.isUploading = true;
    try {
      const formData = new FormData();
      formData.append('file', this.selectedFile);

      const res: any = await this.softwarePackageService.uploadImage(formData);
      return res?.fileDownloadUri || null;
    } catch (err) {
      console.error('Image upload error:', err);
      this.errorMessage = 'Failed to upload image. Please try again.';
      return null;
    } finally {
      this.isUploading = false;
    }
  }

  async handleSubmit(): Promise<void> {
    this.isSubmitting = true;
    this.successMessage = '';
    this.errorMessage = '';

    try {
      let thumbnailUrl: string = this.formData.thumbnail;

      if (this.selectedFile) {
        const uploaded = await this.uploadImage();
        if (!uploaded) {
          this.isSubmitting = false;
          return;
        }
        thumbnailUrl = uploaded;
      }

      const payload = {
        title: this.formData.title,
        description: this.formData.description,
        price: parseFloat(this.formData.price) || 0,
        stock: parseInt(this.formData.stock, 10) || 0,
        category: this.formData.category,
        brand: this.formData.brand,
        discountPercentage: parseFloat(this.formData.discountPercentage) || 0,
        thumbnail: thumbnailUrl || '',
        images: thumbnailUrl ? [thumbnailUrl] : []
      };

      const result = await this.softwarePackageService.addPackage(payload);

      if (result) {
        this.successMessage = `Software package "${result.title}" added successfully!`;

        this.formData = {
          title: '',
          description: '',
          price: '',
          stock: '',
          category: '',
          brand: '',
          discountPercentage: '',
          thumbnail: ''
        };
        this.selectedFile = null;
        this.imagePreview = null;

        setTimeout(() => {
          this.router.navigate(['/packages']);
        }, 2000);
      } else {
        this.errorMessage = 'Failed to add software package. Please try again.';
      }
    } catch (err) {
      console.error(err);
      this.errorMessage = 'An error occurred. Please try again.';
    } finally {
      this.isSubmitting = false;
    }
  }

  goBack(): void {
    this.router.navigate(['/packages']);
  }
}
