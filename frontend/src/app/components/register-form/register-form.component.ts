import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignupDto } from 'src/app/model/dto/signupDto';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {
  registerForm!: FormGroup
  errorMessage = ''

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      name: new FormControl('', [Validators.pattern('^[a-zA-Z]*$'), Validators.maxLength(30)]),
      surname: new FormControl('', [Validators.pattern('^[a-zA-Z]*$'), Validators.maxLength(30)]),
      username: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.minLength(3), Validators.maxLength(20)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9!@#&]*$'), Validators.minLength(8), Validators.maxLength(50)]),
    })
  }

  register(){
    var formData = this.registerForm.value
    var signupDto = new SignupDto(formData.name, formData.surname, formData.email, formData.username, formData.password)
    this.authService.signup(signupDto).subscribe(
      data => {
        this.errorMessage = ''
        this.router.navigate(['/login']);
      },
      error => {
        if(error.error) this.errorMessage = error.error.message
      }
    )
  }

  get name(){
    return this.registerForm.get('name');
  }

  get surname(){
    return this.registerForm.get('surname');
  }

  get password(){
    return this.registerForm.get('password');
  }

  get email(){
    return this.registerForm.get('email');
  }

  get username(){
    return this.registerForm.get('username');
  }
}
