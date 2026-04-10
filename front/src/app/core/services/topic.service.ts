import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from '../models/topic.model';

@Injectable({ providedIn: 'root' })
export class TopicService {
  private readonly API = 'http://localhost:8080/api/topics';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.API, { withCredentials: true });
  }

  subscribe(topicId: number): Observable<void> {
    return this.http.post<void>(`${this.API}/${topicId}/subscribe`, {}, { withCredentials: true });
  }

  unsubscribe(topicId: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${topicId}/unsubscribe`, { withCredentials: true });
  }
}
