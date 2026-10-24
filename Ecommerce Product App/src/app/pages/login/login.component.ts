import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  loading = false;
  errorMessage = '';

  constructor(private auth: AuthService, private router: Router) {}

  async handleLogin(): Promise<void> {
    this.loading = true;
    this.errorMessage = '';
    await this.auth.login({ username: this.username, password: this.password });
    this.loading = false;
  }
}
