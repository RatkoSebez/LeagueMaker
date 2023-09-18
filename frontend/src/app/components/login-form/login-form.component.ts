import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignInRequest } from 'src/app/model/dto/request/SignInRequest';
import { AuthService } from 'src/app/services/auth.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private localStorageService: LocalStorageService
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  login() {
    var formData = this.loginForm.value;
    var request = new SignInRequest(formData.username, formData.password);

    this.authService.signin(request).subscribe({
      next: (data) => {
        this.errorMessage = '';
        this.localStorageService.saveToLocalStorage(data);
        this.authService.updateVariables();
        this.router.navigate(['/']);
      },
      error: (e) => {
        if (e.error) this.errorMessage = e.error.message;
      },
    });
  }
}
