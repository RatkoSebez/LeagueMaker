import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { DomSanitizer } from '@angular/platform-browser';
import { User } from 'src/app/model/User';
import { AuthService } from 'src/app/services/auth.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UpdateUserRequest } from 'src/app/model/dto/request/UpdateUserRequest';
import { ChangePasswordRequest } from 'src/app/model/dto/request/ChangePasswordRequest';
import { ChangeUsernameRequest } from 'src/app/model/dto/request/ChangeUsername';
import { ChangeEmailRequest } from 'src/app/model/dto/request/ChangeEmailRequest';
import { CompetitionService } from 'src/app/services/competition.service';
import { CompetitionResponse } from 'src/app/model/dto/response/CompetitionResponse';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
})
export class UserProfileComponent implements OnInit {
  editUserForm!: FormGroup;
  changePasswordForm!: FormGroup;
  changeUsernameForm!: FormGroup;
  changeEmailForm!: FormGroup;
  user: User = new User();
  imgBase64: any;
  competitions!: CompetitionResponse[];

  constructor(
    private userService: UserService,
    private domSanitizer: DomSanitizer,
    private authService: AuthService,
    private localStorageService: LocalStorageService,
    private competitionService: CompetitionService
  ) {}

  ngOnInit(): void {
    this.initForms();
    this.getUser();
    this.getCompetitions();
  }

  initForms() {
    this.editUserForm = new FormGroup({
      name: new FormControl(this.user.name, [
        Validators.pattern('^[a-zA-Z]*$'),
        Validators.maxLength(30),
      ]),
      surname: new FormControl(this.user.surname, [
        Validators.pattern('^[a-zA-Z]*$'),
        Validators.maxLength(30),
      ]),
      bio: new FormControl(this.user.bio, [Validators.maxLength(500)]),
    });

    this.changePasswordForm = new FormGroup({
      currentPassword: new FormControl('', [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9!@#&]*$'),
        Validators.minLength(8),
        Validators.maxLength(50),
      ]),
      newPassword: new FormControl('', [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9!@#&]*$'),
        Validators.minLength(8),
        Validators.maxLength(50),
      ]),
    });

    this.changeUsernameForm = new FormGroup({
      newUsername: new FormControl(this.user.username, [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_]*$'),
        Validators.minLength(3),
        Validators.maxLength(20),
      ]),
      cupassword: new FormControl('', [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9!@#&]*$'),
        Validators.minLength(8),
        Validators.maxLength(50),
      ]),
    });

    this.changeEmailForm = new FormGroup({
      newEmail: new FormControl(this.user.email, [
        Validators.required,
        Validators.email,
      ]),
      cepassword: new FormControl('', [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9!@#&]*$'),
        Validators.minLength(8),
        Validators.maxLength(50),
      ]),
    });
  }

  getUser() {
    this.userService.getUser().subscribe({
      next: (data) => {
        this.user = data;
        this.imgBase64 = this.domSanitizer.bypassSecurityTrustResourceUrl(
          'data:image/png;base64,' + this.user.profileImageInBase64
        );
        this.initForms();
      },
    });
  }

  getCompetitions() {
    var ids = this.localStorageService.getAdminOfCompetitions();
    if (ids.length == 0) return;
    this.competitionService.getCompetitionNames(ids).subscribe({
      next: (data) => {
        this.competitions = data;
      },
    });
  }

  logout() {
    this.authService.logout();
  }

  editUser() {
    var formData = this.editUserForm.value;
    var request = new UpdateUserRequest(
      formData.name,
      formData.surname,
      formData.bio
    );
    this.userService.setUser(request).subscribe({
      next: (data) => {
        this.user = data;
      },
    });
  }

  changePassword() {
    var formData = this.changePasswordForm.value;
    var request = new ChangePasswordRequest(
      formData.currentPassword,
      formData.newPassword
    );
    this.userService.changePassword(request).subscribe({
      next: (data) => {
        if (data != null) this.localStorageService.saveToLocalStorage(data);
      },
    });
  }

  changeUsername() {
    var formData = this.changeUsernameForm.value;
    var request = new ChangeUsernameRequest(
      formData.newUsername,
      formData.cupassword
    );
    this.userService.changeUsername(request).subscribe({
      next: (data) => {
        if (data != null) this.localStorageService.saveToLocalStorage(data);
        this.user.username = (data as User).username;
      },
    });
  }

  changeEmail() {
    var formData = this.changeEmailForm.value;
    var request = new ChangeEmailRequest(
      formData.cepassword,
      formData.newEmail
    );
    this.userService.changeEmail(request).subscribe({
      next: (data) => {
        if (data != null) {
          this.localStorageService.saveToLocalStorage(data);
          this.user.email = (data as User).email;
        }
      },
    });
  }
}
