import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  admin = true;

  constructor(private router: Router) {}

  ngOnInit(): void {
  }

  redirectLogin(){
    if (this.admin){
      this.router.navigate(['/admin']);
    } else {
      this.router.navigate(['/']);
    }
  }
}
