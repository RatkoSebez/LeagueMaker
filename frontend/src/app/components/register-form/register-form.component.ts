import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignUpRequest } from 'src/app/model/dto/request/SignUpRequest';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css'],
})
export class RegisterFormComponent implements OnInit {
  registerForm!: FormGroup;
  errorMessage = '';

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.registerForm = new FormGroup({
      name: new FormControl('', [
        Validators.pattern('^[a-zA-Z]*$'),
        Validators.maxLength(30),
      ]),
      surname: new FormControl('', [
        Validators.pattern('^[a-zA-Z]*$'),
        Validators.maxLength(30),
      ]),
      username: new FormControl('', [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_]*$'),
        Validators.minLength(3),
        Validators.maxLength(20),
      ]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9!@#&]*$'),
        Validators.minLength(8),
        Validators.maxLength(50),
      ]),
      sex: new FormControl(null, [Validators.required]),
    });
  }

  register() {
    var formData = this.registerForm.value;
    var request = new SignUpRequest(
      formData.name,
      formData.surname,
      formData.email,
      formData.username,
      formData.password,
      formData.sex
    );

    this.authService.signup(request).subscribe({
      next: () => {
        this.errorMessage = '';
        this.router.navigate(['/login']);
      },
      error: (e) => {
        if (e.error) this.errorMessage = e.error.message;
      },
    });
  }

  get sex() {
    return this.registerForm.get('sex');
  }
}
