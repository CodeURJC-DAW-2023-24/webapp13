import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-consult',
  templateUrl: './profile-consult.component.html',
  styleUrls: ['./profile-consult.component.css']
})
export class ProfileConsultComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

}
