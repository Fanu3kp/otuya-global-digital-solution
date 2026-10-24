import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

const API_URL = 'https://blob-spruce-anywhere.ngrok-free.dev/api';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private accessToken = localStorage.getItem('accessToken') || null;
  private refreshToken = localStorage.getItem('refreshToken') || null;
  private user: any = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')!) : null;
  error: string | null = null;
  loading = false;

  constructor(private http: HttpClient, private router: Router) {}

  get isAuthenticated(): boolean {
    return !!this.accessToken;
  }

  get authHeaders(): { [key: string]: string } {
    return this.accessToken ? { Authorization: `Bearer ${this.accessToken}` } : {};
  }

  get isAdmin(): boolean {
    if (!this.user) return false;
    const role = this.user.role;
    const roles = Array.isArray(role) ? role : String(role).split(',').map(r => r.trim());
    return roles.includes('ADMIN');
  }

  restoreSession(): void {
    const storedAccessToken = localStorage.getItem('accessToken');
    const storedRefreshToken = localStorage.getItem('refreshToken');
    const storedUser = localStorage.getItem('user');
    if (storedAccessToken) this.accessToken = storedAccessToken;
    if (storedRefreshToken) this.refreshToken = storedRefreshToken;
    if (storedUser) this.user = JSON.parse(storedUser);
  }

  async login({ username, password }: { username: string; password: string }): Promise<void> {
    if (!username || !password) {
      this.error = 'Username and password are required';
      return;
    }

    this.loading = true;
    this.error = null;

    try {
      const res: any = await this.http.post(`${API_URL}/auth/login`, { username, password }).toPromise();
      const token = res?.token;

      this.accessToken = token;
      this.user = { id: res.id, username: res.username, email: res.email, role: res.role };

      localStorage.setItem('accessToken', this.accessToken!);
      localStorage.setItem('user', JSON.stringify(this.user));

      await this.router.navigate(['/packages']);
    } catch (err: any) {
      console.error('Login error:', err);
      this.error = err.error?.error || err.message || 'Login failed. Please check your credentials.';
    } finally {
      this.loading = false;
    }
  }

  async register({ username, email, password }: { username: string; email: string; password: string }): Promise<void> {
    if (!username || !email || !password) {
      this.error = 'All fields are required';
      return;
    }

    this.loading = true;
    this.error = null;

    try {
      await this.http.post(`${API_URL}/auth/register`, { username, email, password }).toPromise();
      this.error = null;
    } catch (err: any) {
      console.error('Register error:', err);
      this.error = err.error?.error || err.message || 'Registration failed.';
    } finally {
      this.loading = false;
    }
  }

  logout(): void {
    this.accessToken = null;
    this.user = null;
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
