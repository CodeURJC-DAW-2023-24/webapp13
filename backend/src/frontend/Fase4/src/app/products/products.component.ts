import { Component, OnInit } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { map, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-product',
  templateUrl: 'product.component.html',
  styleUrl: 'product.component.css'
})
export class ProductsComponent implements OnInit {
  products: any[] = [];

  constructor(private httpClient: HttpClient) {}

  ngOnInit() {
    this.getProducts();
  }

  getProducts() {
    this.httpClient.get<any[]>('https://localhost:8443/api/users/1/allProducts')
      .pipe(
        map(response => response),
        catchError(error => {
          console.error('Error fetching products:', error);
          return throwError(error);
        })
      )
      .subscribe(
        (response) => {
          this.products = response;
        }
      );
  }
}
