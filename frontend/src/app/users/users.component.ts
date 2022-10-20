import { Component, OnInit } from '@angular/core';
import { UserControllerService, User } from 'generated-client';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  
  users: User[] = [];

  constructor(protected userControllerService: UserControllerService) { }

  ngOnInit(): void {
    this.userControllerService.getAllUsers().subscribe(users => {
      this.users = users;
    });
  }

}
