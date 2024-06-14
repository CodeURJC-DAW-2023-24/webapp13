import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from './Services/product.service'; // Asegúrate de que la ruta es correcta
import { SharedService } from './Services/shared.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Gualapop';
  categories: string[] = [];
  logged = false;

  constructor(private router: Router, private productService: ProductService, private sharedService: SharedService) {}

  ngOnInit(): void {
    this.productService.getAllCategories().subscribe({
      next: (categories: string[]) => {
        this.categories = categories;
        console.log(this.categories); // Verifica que las categorías se están recibiendo correctamente
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

  selectCategory(index: number): void {
    this.sharedService.changeCategory(index);
  }

  searchProducts(term: string): void {
    this.sharedService.changeSearchTerm(term);
  }
}
