import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UpdateUserRequest, UserProfile } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  private readonly API = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  getMe(): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.API}/me`, { withCredentials: true });
  }

  updateMe(request: UpdateUserRequest): Observable<UserProfile> {
    return this.http.put<UserProfile>(`${this.API}/me`, request, { withCredentials: true });
  }
}
