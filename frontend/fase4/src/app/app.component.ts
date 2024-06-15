import { Component, OnInit, booleanAttribute } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from './Services/product.service'; // Asegúrate de que la ruta es correcta
import { SharedService } from './Services/shared.service';
import { AuthService } from './Services/auth.service';
import { UsersService } from './Services/user.service';
import { User } from './Models/user.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Gualapop';
  categories: string[] = [];
  logged = false;
  user: any;

  constructor(private router: Router, private productService: ProductService, private sharedService: SharedService, private authService: AuthService, private userService: UsersService) {}

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
      this.user = this.authService.getUserToken();
        if(this.user.auth[0].authority == 'ROLE_ROLE_ADMIN') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/profile']);
        }
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
