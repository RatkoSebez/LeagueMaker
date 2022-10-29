import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  roles = JSON.parse(localStorage.getItem('roles') || '{}')
  roleUser = false
  roleAdmin = false
  loggedIn = false

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.loggedIn = this.authService.getIsLoggedIn()
    this.roleUser = this.authService.getIsUser()
    this.roleAdmin = this.authService.getIsAdmin()
  }

  logout(){
    this.authService.logout();
  }
}
