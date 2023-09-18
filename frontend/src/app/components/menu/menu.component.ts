import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css'],
})
export class MenuComponent implements OnInit {
  searchForm!: FormGroup;
  roles = JSON.parse(localStorage.getItem('roles') || '{}');
  roleUser = false;
  roleAdmin = false;
  loggedIn = false;
  imgBase64: any;

  constructor(
    private authService: AuthService,
    private domSanitizer: DomSanitizer,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loggedIn = this.authService.getIsLoggedIn();
    this.roleUser = this.authService.getIsUser();
    this.roleAdmin = this.authService.getIsAdmin();

    if (this.loggedIn === true) {
    }
    // console.log(localStorage.getItem("base64Image"));
    // this.imgBase64 = localStorage.getItem("base64Image")
    // this.imgBase64 = this.domSanitizer.bypassSecurityTrustResourceUrl(localStorage.getItem("base64Image") || "");
  }

  initForm() {
    this.searchForm = new FormGroup({
      searchQuery: new FormControl('', [Validators.required]),
    });
  }

  search() {
    var formData = this.searchForm.value;
    this.router.navigate(['/search/' + formData.searchQuery]);
  }
}
