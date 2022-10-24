import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {FormControl, Validators} from '@angular/forms';

import { BASE_PATH } from 'generated-client';
import { UserService } from '../userService';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  emailControl = new FormControl('', [Validators.required, Validators.email]);
  passwordControl = new FormControl('', [Validators.required]);

  email: string = "";
  password: string = "";
  rememberMe: boolean = false;
  basePath: string;

  constructor(protected userService: UserService, @Inject(BASE_PATH) basePath: string, protected router: Router) {
    this.basePath = basePath;
  }

  ngOnInit(): void {
  }

  login(): void {
    this.userService.login(this.email, this.password).subscribe(() => {
      this.userService.setLoggedIn(true);
      this.router.navigate(["/my-orders"]);
    });
  }

  getErrorMessage() {
    if (this.emailControl.hasError('required')) {
      return 'You must enter a value';
    }

    return this.emailControl.hasError('email') ? 'Not a valid email' : '';
  }

  getPwdErrorMessage() {
    if (this.passwordControl.hasError('required')) {
      return 'You must enter a value';
    }
    return '';
  }

}
