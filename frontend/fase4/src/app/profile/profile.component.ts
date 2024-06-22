import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { UsersService } from '../Services/user.service';
import { Chart, PieController, ArcElement, Legend, Tooltip, Title, CategoryScale } from 'chart.js';
import { User } from '../Models/user.model';
import { Product } from '../Models/product.model';
import { ProductService } from '../Services/product.service';
import { UntypedFormBuilder, UntypedFormGroup, Validators, } from '@angular/forms';
import * as bcrypt from 'bcryptjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  formulario: UntypedFormGroup;
  user?: User;
  newUser?: User;
  username:any;
  imageURL?:string;
  activeTab: string = 'Products';
  products!: Product[];
  user1: any;

  constructor(private router: Router, private formBuilder: UntypedFormBuilder, private authService: AuthService, private userService: UsersService, private productService: ProductService) {
    Chart.register(PieController, ArcElement, Legend, Tooltip, Title, CategoryScale);
    this.formulario = this.formBuilder.group({
      Name: [''],
      Username: [''],
      CurrentPassword: [''],
      Password: [''],
      RepeatPassword: [''],
      imageFile: [''],
    });
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
    if (this.formulario.valid && this.newUser && this.user?.encodedPassword) {
      const currentPassword = this.formulario.value.CurrentPassword
      console.log("Formulario válido, enviar datos:", this.formulario.value);
      if (this.formulario.value.Name !== ''){
        this.newUser.fullName = this.formulario.value.Name
      }
      if (this.formulario.value.Username !== ''){
        this.newUser.username = this.formulario.value.Username
      }
      if (this.formulario.value.Image !== ''){
        const imageFile = this.formulario.get('imageFile')?.value
        this.newUser.userImg = imageFile
      }
      if (bcrypt.compareSync(this.formulario.value.CurrentPassword, this.user.encodedPassword)
        && this.formulario.value.Password !== ''
        && this.formulario.value.RepeatPassword == this.formulario.value.Password)
        {
          this.newUser.encodedPassword = this.formulario.value.Password

      }else if (this.formulario.value.Password !== '' && this.formulario.value.RepeatPassword !== ''){
        alert("Error, revisa las contraseñas.");
      }
      this.userService.updateUser(this.newUser, currentPassword).subscribe();
      this.authService.logout();
      this.router.navigate(['/login'])
    } else {
      console.log("Formulario inválido, revisa los campos.");
    }
  }

  onFileChange(event: any) {
    debugger;
    const file = event.target.files[0];
    this.formulario.patchValue({
      imageFile: file
    });
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
