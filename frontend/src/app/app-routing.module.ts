import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LeagueDetailsPageComponent } from './pages/league-details-page/league-details-page.component';
import { LeaguePageComponent } from './pages/league-page/league-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';

const routes: Routes = [
  {path: "", component: HomePageComponent},
  {path: "register", component: RegisterPageComponent},
  {path: "login", component: LoginPageComponent},
  {path: "league", component: LeaguePageComponent},
  {path: 'league/:id', component: LeagueDetailsPageComponent},
  {path: "**", component: NotFoundPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
