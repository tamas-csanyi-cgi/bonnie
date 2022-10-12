import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'bonnie-header',
  templateUrl: './bonnie-header.component.html',
  styleUrls: ['./bonnie-header.component.css']
})
export class BonnieHeaderComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  toAllOrders() {
    this.router.navigate(["/all-orders"]);
  }

  toMyOrders() {
    this.router.navigate(["/my-orders"]);
  }

  toUnassignedOrders() {
    this.router.navigate(["/unassigned-orders"]);
  }

  toUsers() {
    this.router.navigate(["/users"]);
  }

}
