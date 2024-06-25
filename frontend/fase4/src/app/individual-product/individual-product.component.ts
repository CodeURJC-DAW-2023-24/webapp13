import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../Models/product.model';
import { ProductService } from '../Services/product.service';
import { User } from '../Models/user.model';
import { UsersService } from '../Services/user.service';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-individual-product',
  templateUrl: './individual-product.component.html',
  styleUrls: ['./individual-product.component.css']
})
export class IndividualProductComponent implements OnInit {
  product!: Product;
  imageUrl: string | undefined;
  user!: User;
  userImageUrl: string | undefined;
  related!: Product[];

  constructor(
    private route: ActivatedRoute, 
    private productService: ProductService, 
    private userService: UsersService, 
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.pipe(
      switchMap(params => {
        const id = Number(params['id']);
        return this.productService.getProductById(id);
      })
    ).subscribe(
      (data: Product) => {
        this.product = data;
        this.loadProductDetails(data);
      },
      (error: any) => {
        console.error('Error loading product:', error);
        this.router.navigate(['/error']);
      }
    );
  }

  private loadProductDetails(product: Product): void {
    this.userService.getUser(product.owner).subscribe(
      (user: User) => {
        this.user = user;
        this.loadUserImage(user.userID);
      },
      (error: any) => {
        console.error('Error loading user:', error);
      }
    );
    this.loadProductImage(product.id);
    this.loadRelatedProducts(product.id);
  }

  private loadUserImage(userId: number): void {
    this.userService.getUserImageById(userId).subscribe(
      (userImg: Blob) => {
        this.userImageUrl = URL.createObjectURL(userImg);
      },
      (error: any) => {
        console.error('Error loading user image:', error);
      }
    );
  }

  private loadProductImage(productId: number): void {
    this.productService.getImageById(productId).subscribe(
      (image: Blob) => {
        this.imageUrl = URL.createObjectURL(image);
      },
      (error: any) => {
        console.error('Error loading product image:', error);
      }
    );
  }

  private loadRelatedProducts(productId: number): void {
    this.productService.getSimilarProducts(productId, 0, 8).subscribe(
      (products: Product[]) => {
        this.related = products;
        this.loadRelatedProductImages(products);
      },
      (error: any) => {
        console.error('Error loading related products:', error);
      }
    );
  }

  private loadRelatedProductImages(products: Product[]): void {
    products.forEach(product => {
      this.productService.getImageById(product.id).subscribe(
        (prodImg: Blob) => {
          product.imageUrl = URL.createObjectURL(prodImg);
        },
        (error: any) => {
          console.error('Error loading related product image:', error);
        }
      );
    });
  }
}
