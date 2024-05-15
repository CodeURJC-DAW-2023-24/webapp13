import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductType } from './Models/product-type.model';
import { ProductService } from './Services/product.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Gualapop';
  categories: ProductType[] = [];
  logged = false;

  constructor(private router: Router, private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getAllCategories().subscribe({
      next: (categories: ProductType[]) => {
        this.categories = categories;
      },
      error: (err) => {
        console.error('Error fetching categories', err);
      }
    });
  }

  redirectProfile(): void {
    if (this.logged) {
      this.router.navigate(['/profile']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  searchProducts(): void {
  }
}
