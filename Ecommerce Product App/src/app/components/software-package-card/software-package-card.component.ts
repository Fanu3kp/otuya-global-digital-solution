import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { SoftwarePackage } from '../../models/software-package';

@Component({
  selector: 'app-software-package-card',
  templateUrl: './software-package-card.component.html',
  styleUrls: ['./software-package-card.component.css']
})
export class SoftwarePackageCardComponent {
  @Input() package!: SoftwarePackage;
  @Output() click = new EventEmitter<SoftwarePackage>();

  constructor(private router: Router) {}

  onClick(): void {
    this.click.emit(this.package);
  }

  orderPackage(event: Event): void {
    event.stopPropagation();
    this.router.navigate(['/packages', this.package.id, 'order']);
  }
}
