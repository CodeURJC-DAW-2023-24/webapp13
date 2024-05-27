import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { UsersService } from '../Services/user.service';
import { User } from '../Models/user.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: any;
  username:any;
  imageURL:string;
  //user: User;

  constructor(private router: Router, private authService: AuthService, private userService: UsersService) {
    this.imageURL = '';
  }

  ngOnInit(): void {
    this.username = this.userService.getUserInfo();
    this.user = this.getUserDetails(this.username);
  }

  addProduct(){
    this.router.navigate(['/newProduct'])
  }

  getUserDetails(username: string): void {
    this.userService.getUserByUsername(username).subscribe(
      (userData) => {
        this.user = userData;
        console.log(this.user);
      },
      (error) => {
        console.error('Error al obtener los detalles del usuario:', error);
      }
    );
  }
}
