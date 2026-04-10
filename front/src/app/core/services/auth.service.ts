import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API = 'http://localhost:8080/api/auth';

  private _isLoggedIn$ = new BehaviorSubject<boolean>(false);
  readonly isLoggedIn$ = this._isLoggedIn$.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  register(request: RegisterRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.API}/register`, request, { withCredentials: true })
      .pipe(tap(() => this._isLoggedIn$.next(true)));
  }

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.API}/login`, request, { withCredentials: true })
      .pipe(tap(() => this._isLoggedIn$.next(true)));
  }

  logout(): void {
    this.http
      .post<AuthResponse>(`${this.API}/logout`, {}, { withCredentials: true })
      .subscribe({
        next: () => this._onLoggedOut(),
        error: () => this._onLoggedOut(),
      });
  }

  private _onLoggedOut(): void {
    this._isLoggedIn$.next(false);
    this.router.navigate(['/']);
  }
}
