import { Component, Inject, OnInit } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import { Router } from '@angular/router';

import { MatSnackBar } from '@angular/material/snack-bar';

import { BASE_PATH } from 'generated-client';
import { UserService } from '../userService';

const snackBarConfig = {
  duration: 5 * 1000
}

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

  constructor(protected userService: UserService,
    @Inject(BASE_PATH) basePath: string,
    protected router: Router,
    private _snackBar: MatSnackBar) {
    this.basePath = basePath;
  }

  ngOnInit(): void {
  }

  login(): void {
    this.userService.login(this.email, this.password).subscribe(() => {
      this.userService.setLoggedIn(true);
      this.router.navigate(["/my-orders"]);
    }, err => {
      this._snackBar.open("Error during login! Please try again later!", 'OK', snackBarConfig);
      console.error(err);
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
