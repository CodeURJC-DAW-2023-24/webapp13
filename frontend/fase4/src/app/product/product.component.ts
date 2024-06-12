import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../Models/product.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../Services/product.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit{
  product!: Product;
  rating: number = 0;
  stars: number[] = [1, 2, 3, 4, 5];

  constructor(private route: ActivatedRoute, private productService: ProductService, private router: Router) {
  }

  ngOnInit(): void {
    this.getInfo();
  }

  getInfo(): void{

    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id){
      this.productService.getProductById(id).subscribe(
        (data: Product) => {
          this.product = data;
        }
      )
    }

  }

  rateProduct(rating: number): void {
    this.rating = rating;
    console.log(rating);
  }

  purchaseProduct(): void {
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
    this.router.navigate(['/profile']);
  }

}
