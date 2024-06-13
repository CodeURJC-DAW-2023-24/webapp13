import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { UsersService } from '../Services/user.service';
import { Chart } from 'chart.js';
import { User } from '../Models/user.model';
import { Product } from '../Models/product.model';
import { ProductService } from '../Services/product.service';

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
  activeTab: string = 'Products';
  products!: Product[];

  constructor(private router: Router, private authService: AuthService, private userService: UsersService, private productService: ProductService) {
    this.imageURL = '';
  }

  ngOnInit(): void {
    this.username = this.userService.getUserInfo();
    this.user = this.getUserDetails(this.username);
    this.userService.getUserByUsername(this.username).subscribe(
      (user: User) => {
        this.userService.getUserProductsById(user.userID).subscribe(
          (data: Product[]) => {
            this.products = data;
          }
        )
      }
    )
  }

  setActiveTab(tabName: string) {
    this.activeTab = tabName;
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

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe(
      () => {
        this.products = this.products.filter(product => product.id !== id);
      },
      (error: any) => {
        console.log(error);
      }
    )
  }
}
