import { Component, OnInit, booleanAttribute } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from './Services/product.service'; // Asegúrate de que la ruta es correcta
import { SharedService } from './Services/shared.service';
import { AuthService } from './Services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Gualapop';
  categories: string[] = [];
  logged = false;

  constructor(private router: Router, private productService: ProductService, private sharedService: SharedService, private authService: AuthService) {}

  ngOnInit(): void {
    this.productService.getAllCategories().subscribe({
      next: (categories: string[]) => {
        this.categories = categories;
      },
      error: (err) => {
        console.error('Error fetching categories', err);
      }
    });
  }

  redirectProfile(): void {
    if (this.authService.isLoggedIn()) {
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
