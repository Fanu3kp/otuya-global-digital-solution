import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
    discountPercentage: ''
  };
  selectedFile: File | null = null;
  imagePreview: string | null = null;
  isUploading = false;
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private router: Router,
    private softwarePackageService: SoftwarePackageService
  ) {}

  ngOnInit(): void {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = e => {
        this.imagePreview = e.target?.result as string;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  removeImage(): void {
    this.selectedFile = null;
    this.imagePreview = null;
  }

  async handleSubmit(): Promise<void> {
    if (!this.formData.title || !this.formData.description || !this.formData.price || !this.formData.stock) {
      this.errorMessage = 'Please fill in all required fields';
      this.successMessage = '';
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    try {
      let imageUrl = '';
      if (this.selectedFile) {
        this.isUploading = true;
        imageUrl = await this.softwarePackageService.uploadImage(this.selectedFile) || '';
        this.isUploading = false;
      }

      const payload = {
        title: this.formData.title,
        description: this.formData.description,
        price: parseFloat(this.formData.price) || 0,
        stock: parseInt(this.formData.stock, 10) || 0,
        category: this.formData.category,
        brand: this.formData.brand,
        discountPercentage: parseFloat(this.formData.discountPercentage) || 0,
        thumbnail: imageUrl,
        images: imageUrl ? [imageUrl] : []
      };

      const result = await this.softwarePackageService.addPackage(payload);
      if (result) {
        this.successMessage = Software package "" added successfully!;
        this.formData = {
          title: '',
          description: '',
          price: '',
          stock: '',
          category: '',
          brand: '',
          discountPercentage: ''
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
      this.isUploading = false;
    }
  }

  goBack(): void {
    this.router.navigate(['/packages']);
  }
}
