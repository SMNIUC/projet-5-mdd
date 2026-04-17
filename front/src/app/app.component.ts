import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  readonly isLoggedIn$: Observable<boolean>;
  readonly year = new Date().getFullYear();

  constructor(private authService: AuthService) {
    this.isLoggedIn$ = authService.isLoggedIn$;
  }

  onLogout(): void {
    this.authService.logout();
  }
}
