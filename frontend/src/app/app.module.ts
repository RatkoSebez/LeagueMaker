import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './components/menu/menu.component';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatButtonModule } from '@angular/material/button'
import { MatTooltipModule } from '@angular/material/tooltip';
import { LeaguePageComponent } from './pages/league-page/league-page.component';
import { LeagueFormComponent } from './components/league-form/league-form.component'
import { AddHeaderInterceptor } from './interceptors/AddHeaderInterceptor';
import { LeagueDetailsPageComponent } from './pages/league-details-page/league-details-page.component';
import { LeagueStandingsComponent } from './components/league-standings/league-standings.component';
import { LeagueScheduleComponent } from './components/league-schedule/league-schedule.component';
import { MatchComponentComponent } from './components/match-component/match-component.component';
import { MyLeaguesComponent } from './components/my-leagues/my-leagues.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    RegisterFormComponent,
    FooterComponent,
    HomePageComponent,
    LoginPageComponent,
    RegisterPageComponent,
    NotFoundPageComponent,
    LoginFormComponent,
    LeaguePageComponent,
    LeagueFormComponent,
    LeagueDetailsPageComponent,
    LeagueStandingsComponent,
    LeagueScheduleComponent,
    MatchComponentComponent,
    MyLeaguesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatTooltipModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AddHeaderInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
