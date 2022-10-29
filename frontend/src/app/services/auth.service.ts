import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { SigninDto } from '../model/dto/SigninDto';
import { User } from '../model/User';
import { SignupDto } from '../model/dto/signupDto';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private path = 'http://localhost:8080/api/v1/auth/';
  private roleUser = false
  private roleAdmin = false
  private loggedIn = false
  private adminOfCompetitions = JSON.parse(localStorage.getItem('adminOfCompetitions') || '{}');

  constructor(private http: HttpClient, private router: Router) { 
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    // read roles from local storage and get store information about role
    var roles = JSON.parse(localStorage.getItem('roles') || '{}');
    for(let i = 0; i < roles.length; i++) {
        let role = roles[i];
        if(role === 'ROLE_USER') this.roleUser = true, this.loggedIn = true;
        if(role === 'ROLE_ADMIN') this.roleAdmin = true, this.loggedIn = true;
    }
  }

  signin(signinDto: SigninDto){
    return this.http.post(this.path + 'signin', signinDto)
  }

  signup(signupDto: SignupDto){
    return this.http.post(this.path + 'signup', signupDto)
  }

  logout(){
    localStorage.clear()
    this.router.navigate(['/']);
  }

  getIsLoggedIn(){
    return this.loggedIn;
  }

  getIsUser(){
    return this.roleUser;
  }

  getIsAdmin(){
    return this.roleAdmin;
  }

  isAdminOfCompetition(competitionId: number){
    for(let i=0; i<this.adminOfCompetitions.length; i++){
      if(this.adminOfCompetitions[i] === competitionId) return true;
    }
    return false;
  }
}
