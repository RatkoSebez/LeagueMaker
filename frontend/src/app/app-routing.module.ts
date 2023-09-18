import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LeagueDetailsPageComponent } from './pages/league-details-page/league-details-page.component';
import { LeaguePageComponent } from './pages/league-page/league-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { SearchPageComponent } from './pages/search-page/search-page.component';
import { TournamentDetailsPageComponent } from './pages/tournament-details-page/tournament-details-page.component';
import { TournamentPageComponent } from './pages/tournament-page/tournament-page.component';
import { UserProfilePageComponent } from './pages/user-profile-page/user-profile-page.component';
import { CompetitorPageComponent } from './pages/competitor-page/competitor-page.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'register', component: RegisterPageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'league', component: LeaguePageComponent },
  { path: 'league/:id', component: LeagueDetailsPageComponent },
  { path: 'tournament', component: TournamentPageComponent },
  { path: 'tournament/:id', component: TournamentDetailsPageComponent },
  { path: 'profile', component: UserProfilePageComponent },
  { path: 'search/:query', component: SearchPageComponent },
  { path: 'competitor/:id', component: CompetitorPageComponent },
  { path: '**', component: NotFoundPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
