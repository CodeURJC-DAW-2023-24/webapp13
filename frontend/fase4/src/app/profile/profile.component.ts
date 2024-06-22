import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { UsersService } from '../Services/user.service';
import { Chart, PieController, ArcElement, Legend, Tooltip, Title, CategoryScale } from 'chart.js';
import { User } from '../Models/user.model';
import { Product } from '../Models/product.model';
import { ProductService } from '../Services/product.service';
import { FormGroup, UntypedFormBuilder, UntypedFormGroup, Validators, } from '@angular/forms';
import * as bcrypt from 'bcryptjs';
import { NONE_TYPE } from '@angular/compiler';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  user?: User;
  newUser?: User;
  username:any;
  imageURL?:string;
  activeTab: string = 'Products';
  products!: Product[];
  user1: any;
  formData: any = {
    fullName: '',
    username: '',
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
    imageFile: null
  };

  constructor(private router: Router, private formBuilder: UntypedFormBuilder, private authService: AuthService, private userService: UsersService, private productService: ProductService) {
    Chart.register(PieController, ArcElement, Legend, Tooltip, Title, CategoryScale);
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.user1 = this.authService.getUserToken();
        if(this.user1.auth[0].authority != 'ROLE_ROLE_USER') {
          this.router.navigate(['/']);
        }
      }
    else {
      this.router.navigate(['/']);
    }
    this.username = this.userService.getUserInfo();
    this.userService.getUserByUsername(this.username).subscribe(
      (user: User) => {
        this.user = user
        this.newUser = user
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
    if (this.activeTab === 'Beneficts' && this.user) {
      setTimeout(() => {
        this.loadChart();
      }, 0);
    }
  }

  addProduct(){
    this.router.navigate(['/newProduct'])
  }

  loadChart() {
    const canvas = document.getElementById('balanceChart') as HTMLCanvasElement;
    const ctx = canvas?.getContext('2d');

    if (ctx) {
      new Chart(ctx, {
        type: 'pie',
        data: {
          labels: ['Ingresos', 'Gastos'],
          datasets: [{
            data: [this.user?.income, this.user?.expense],
            backgroundColor: ['#36A2EB', '#FF6384']
          }]
        },
        options: {
          plugins:{
            legend: {
              display: true,
              position: 'right',
              align: 'start',
              labels: {
                boxWidth: 15,
                font: {
                  size: 14,
                  style: 'normal'
                }
              }
            }
          }
        }
      });
    } else {
      console.error('Failed to get canvas context');
    }
  }

  onSettings(){
    if(this.user){
    const formData = new FormData();
    debugger;
    formData.append('fullName', this.formData.fullName);
    formData.append('username', this.formData.username);
    formData.append('currentPassword', this.formData.currentPassword);
    formData.append('newPassword', this.formData.newPassword);
    formData.append('confirmPassword', this.formData.confirmPassword);
    formData.append('imageFile', this.formData.imageFile);

    this.userService.updateUser(this.user?.userID, formData)
      .subscribe(
        response => {
          console.log('User updated successfully');
        },
        error => {
          alert(`Error: ${error}`);

        }
      );
    }
    this.authService.logout();
    this.router.navigate(['/login'])
  }

  onDelete(){
    if(this.user){
      this.userService.deleteUser(this.user.userID).subscribe();
      this.authService.logout();
      this.router.navigate(['/login'])
    }
  }

  onFileChange(event: Event) {
    debugger;
    const inputElement = event.target as HTMLInputElement;
    if (inputElement.files && inputElement.files.length > 0) {
      const file = inputElement.files[0];
      this.formData.imageFile = file;
    }
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
