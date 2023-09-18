import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { SignInRequest } from '../model/dto/request/SignInRequest';
import { SignUpRequest } from '../model/dto/request/SignUpRequest';
import * as myGlobals from 'src/globals';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private path = myGlobals.path + '/auth/';
  private roleUser = false;
  private roleAdmin = false;
  private loggedIn = false;

  constructor(private http: HttpClient, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.updateVariables();
  }

  updateVariables() {
    this.roleUser = false;
    this.roleAdmin = false;
    this.loggedIn = false;
    // read roles from local storage and get store information about role
    var roles = JSON.parse(localStorage.getItem('roles') || '{}');
    for (let i = 0; i < roles.length; i++) {
      let role = roles[i];
      if (role === 'ROLE_USER') (this.roleUser = true), (this.loggedIn = true);
      if (role === 'ROLE_ADMIN')
        (this.roleAdmin = true), (this.loggedIn = true);
    }
  }

  signin(data: SignInRequest) {
    return this.http.post(this.path + 'signin', data);
  }

  signup(signupDto: SignUpRequest) {
    return this.http.post(this.path + 'signup', signupDto);
  }

  logout() {
    localStorage.clear();
    this.updateVariables();
    this.router.navigate(['/']);
  }

  getIsLoggedIn() {
    return this.loggedIn;
  }

  getIsUser() {
    return this.roleUser;
  }

  getIsAdmin() {
    return this.roleAdmin;
  }

  isAdminOfCompetition(competitionId: number) {
    var adminOfCompetitions = JSON.parse(
      localStorage.getItem('adminOfCompetitions') || '{}'
    );
    for (let i = 0; i < adminOfCompetitions.length; i++) {
      if (adminOfCompetitions[i] === competitionId) return true;
    }
    return false;
  }
}
