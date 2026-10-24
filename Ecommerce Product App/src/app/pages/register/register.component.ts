import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  loading = false;
  errorMessage = '';

  constructor(private auth: AuthService, private router: Router) {}

  async handleRegister(): Promise<void> {
    this.loading = true;
    this.errorMessage = '';
    await this.auth.register({ username: this.username, email: this.email, password: this.password });
    this.loading = false;
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
