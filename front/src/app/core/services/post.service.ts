import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreatePostRequest, Post } from '../models/post.model';

@Injectable({ providedIn: 'root' })
export class PostService {
  private readonly API = 'http://localhost:8080/api/posts';

  constructor(private http: HttpClient) {}

  getFeed(order: 'asc' | 'desc' = 'desc'): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.API}/feed`, {
      params: { order },
      withCredentials: true,
    });
  }

  create(request: CreatePostRequest): Observable<Post> {
    return this.http.post<Post>(this.API, request, { withCredentials: true });
  }
}
