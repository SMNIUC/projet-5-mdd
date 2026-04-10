import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { LoginComponent } from './features/auth/login/login.component';
import { FeedComponent } from './features/feed/feed.component';
import { ThemesComponent } from './features/themes/themes.component';
import { ProfileComponent } from './features/profile/profile.component';
import { WriteArticleComponent } from './features/article/write-article/write-article.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'feed', component: FeedComponent },
  { path: 'themes', component: ThemesComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'write', component: WriteArticleComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
