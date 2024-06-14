import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { UsersService } from '../Services/user.service';
import { Chart, PieController, ArcElement, Legend, Tooltip, Title, CategoryScale } from 'chart.js';
import { User } from '../Models/user.model';
import { Product } from '../Models/product.model';
import { ProductService } from '../Services/product.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, AfterViewInit {

  user?: User;
  username:any;
  imageURL?:string;
  activeTab: string = 'Products';
  products!: Product[];

  constructor(private router: Router, private authService: AuthService, private userService: UsersService, private productService: ProductService) {
    Chart.register(PieController, ArcElement, Legend, Tooltip, Title, CategoryScale);
  }

  ngOnInit(): void {
    this.username = this.userService.getUserInfo();
    this.userService.getUserByUsername(this.username).subscribe(
      (user: User) => {
        this.user = user
        this.userService.getUserProductsById(user.userID).subscribe(
          (data: Product[]) => {
            this.products = data;
          }
        )
      }
    )
  }

  ngAfterViewInit() {
    if (this.activeTab === 'Beneficts') {
      /*this.loadChart();*/
    }
  }

  setActiveTab(tabName: string) {
    this.activeTab = tabName;
    if (this.activeTab === 'Beneficts' && this.user) {
      setTimeout(() => {
        this.loadChart();
      }, 0); // Llama a loadChart después de que Angular complete las actualizaciones del DOM
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
