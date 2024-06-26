import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../Models/product.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductService } from '../Services/product.service';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../Services/auth.service';
import { UsersService } from '../Services/user.service';
import { User } from '../Models/user.model';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit{
  product!: Product;
  rating: number = 0;
  stars: number[] = [1, 2, 3, 4, 5];
  user: any;

  constructor(private route: ActivatedRoute, private productService: ProductService, private router: Router, private authService: AuthService, private userService: UsersService) {
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.user = this.authService.getUserToken();
        if(this.user.auth[0].authority != 'ROLE_ROLE_USER') {
          this.router.navigate(['/']);
        }
      }
    else {
      this.router.navigate(['/']);
    }
    this.getInfo();
  }

  getInfo(): void{

    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id){
      this.productService.getProductById(id).subscribe(
        (data: Product) => {
          this.product = data;
          this.userService.getUser(data.owner).subscribe(
            (user: User) => {
              if (this.userService.getUserInfo() == user.username) {
                this.router.navigate(['/']);
              }
            }
          );
        },
        (error: any) => {
          console.log(error);
          this.router.navigate(['/error']);
        }
      )
    }

  }

  rateProduct(rating: number): void {
    this.rating = rating;
    console.log(rating);
  }

  async purchaseProduct(): Promise<void> {
    this.productService.purchaseProduct(this.product.id, this.rating).subscribe(
      (response: any) => {
        console.log('Producto comprado exitosamente:', response);
        // Navega a la página de login después de registrar el usuario
      },
      (error: any) => {
        console.error('Error al comprar el producto:', error);
        // Manejo de errores adicional si es necesario
      }
    );
    this.productService.getPdf(this.product.id).subscribe(
      (pdf: Blob) => {
        const url = window.URL.createObjectURL(pdf);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'bill.pdf'; // Nombre del archivo a descargar
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
      }, error => {
        console.error('Error downloading PDF', error);
      }
    );
    await this.wait(0.1);
    this.router.navigate(['/']);
  }

  wait(seconds: number): Promise<void> {
    return new Promise(resolve => {
      setTimeout(resolve, seconds * 1000);
    });
  }

}
