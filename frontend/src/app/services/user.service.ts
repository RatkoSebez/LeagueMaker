import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/User';
import { UpdateUserRequest } from '../model/dto/request/UpdateUserRequest';
import { ChangePasswordRequest } from '../model/dto/request/ChangePasswordRequest';
import { ChangeUsernameRequest } from '../model/dto/request/ChangeUsername';
import { ChangeEmailRequest } from '../model/dto/request/ChangeEmailRequest';
import * as myGlobals from 'src/globals';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private path = myGlobals.path + '/user';

  constructor(private http: HttpClient, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  getUser() {
    return this.http.get<User>(this.path);
  }

  setUser(request: UpdateUserRequest) {
    return this.http.post<User>(this.path, request);
  }

  changePassword(data: ChangePasswordRequest) {
    return this.http.post(this.path + '/password', data);
  }

  changeUsername(request: ChangeUsernameRequest) {
    return this.http.post(this.path + '/username', request);
  }

  changeEmail(request: ChangeEmailRequest) {
    return this.http.post(this.path + '/email', request);
  }
}
