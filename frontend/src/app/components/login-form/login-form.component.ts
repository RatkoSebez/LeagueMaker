import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SigninDto } from 'src/app/model/dto/SigninDto';
import { AuthService } from 'src/app/services/auth.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  loginForm!: FormGroup
  errorMessage = ''

  constructor(private http: HttpClient, private router: Router, private authService: AuthService, private lsService: LocalStorageService) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    })
  }

  login(){
    var formData = this.loginForm.value
    var signinDto = new SigninDto(formData.username, formData.password)
    this.authService.signin(signinDto).subscribe(
      data => {
        this.errorMessage = ''
        this.lsService.saveToLocalStorage(data)
        // console.log(data)
        this.router.navigate(['/']);
      },
      error => {
        if(error.error) this.errorMessage = error.error.message
      }
    )
  }

  get username(){
    return this.loginForm.get('username');
  }

  get password(){
    return this.loginForm.get('password');
  }
}
