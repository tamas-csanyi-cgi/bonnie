import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BASE_PATH } from 'generated-client';
import { UserService } from '../userService';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  email: string = "";
  password: string = "";
  basePath: string;

  constructor(protected userService: UserService, @Inject(BASE_PATH) basePath: string, protected router: Router) {
    this.basePath = basePath;
  }

  ngOnInit(): void {
  }

  login(): void {
    this.userService.login(this.email, this.password).subscribe(() => {
      this.router.navigate(["/my-orders"]);
    });
  }

}
