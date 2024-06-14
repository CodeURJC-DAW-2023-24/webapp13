import { Component, OnDestroy, OnInit } from '@angular/core';
import { Product } from '../Models/product.model';
import { ProductService } from '../Services/product.service';
import { Subscription } from 'rxjs';
import { SharedService } from '../Services/shared.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit, OnDestroy {
  products: Product[] = [];
  page: number = 1;
  size: number = 8;
  productType: number = 0;
  categorySubscription!: Subscription;

  constructor(private productService: ProductService, private sharedService: SharedService) {}

  ngOnInit(): void {
    this.loadProducts();

    this.categorySubscription = this.sharedService.categoryChanged$.subscribe(index => {
      this.loadProductsByCategory(index);
    });
  }

  ngOnDestroy(): void {
    this.categorySubscription.unsubscribe();
  }

  loadProducts(): void {
    this.productService.getAllProducts(this.page, this.size, this.productType).subscribe(
      (data: Product[]) => {
        this.products = data;
      },
      (      error: any) => {
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
      (      error: any) => {
        console.error('Error fetching more products:', error);
      }
    );
  }

  loadProductsByCategory(index: number): void {
    const categoryId = index + 1; // Assuming the index corresponds to the category ID
    this.productService.getProductsByType(categoryId).subscribe(
      (data: any) => {
        this.products = data;
      },
      (error: any) => {
        console.error('Error fetching products by category:', error);
      }
    );
  }
}