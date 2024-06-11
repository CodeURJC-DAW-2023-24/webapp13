import { Component, OnInit } from '@angular/core';
import { Product } from '../Models/product.model';
import { ProductService } from '../Services/product.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  products: Product[] = [];
  page: number = 1;
  size: number = 8;
  productType: number = 0;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAllProducts(this.page, this.size, this.productType).subscribe(
      (data: Product[]) => {
        this.products = data;
      },
      error => {
        console.error('Error fetching products:', error);
      }
    );
  }

  loadMoreProducts(): void {
    this.page++;
    this.productService.getAllProducts(this.page, this.size, this.productType).subscribe(
      (data: Product[]) => {
        this.products = this.products.concat(data);
      },
      error => {
        console.error('Error fetching more products:', error);
      }
    );
  }
}