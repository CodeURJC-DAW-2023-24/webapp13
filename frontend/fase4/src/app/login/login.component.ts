import { Component, OnInit } from '@angular/core';
import { FormsModule, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  user:any;
  loginObj: Login;
  loginURL = '/api/auth/login';
  fail: Boolean = false;

  constructor(private http:HttpClient, private router: Router, private authService: AuthService){
    this.loginObj = new Login();
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.user = this.authService.getUserToken();
        if(this.user.auth[0].authority == 'ROLE_ROLE_ADMIN') {
          this.router.navigate(['/admin']);
        }
        else if (this.user.auth[0].authority == 'ROLE_ROLE_USER') {
          this.router.navigate(['/profile']);
        }
      }
  }

  onLogin() {
    this.http.post(this.loginURL, this.loginObj, { observe: 'response' }).subscribe((res:any)=>{
      if(!res.error) {
        alert('Login success');
        localStorage.setItem('Token', res.body.accessToken.tokenValue);
        this.user = this.authService.getUserToken();
        if(this.user.auth[0].authority == 'ROLE_ROLE_ADMIN') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/profile']);
        }
      } else {
        alert(res.message)
      }
    },() => {
      this.fail = true;
    })
  }
}
export class Login {
  username:string;
  password:string;
  constructor(){
    this.username = "";
    this.password = "";
  }
}
