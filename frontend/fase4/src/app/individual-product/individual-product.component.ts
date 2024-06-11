import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../Models/product.model';
import { ProductService } from '../Services/product.service';
import { User } from '../Models/user.model';
import { UsersService } from '../Services/user.service';

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

  constructor(private route: ActivatedRoute, private productService: ProductService, private userService: UsersService) {
  }

  ngOnInit(): void {
    this.getInfo();
  }

  getInfo(): void{
    //this.router.navigate(['/product/id'])
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id){
      this.productService.getProductById(id).subscribe(
        (data: Product) => {
          this.product = data;
          this.userService.getUser(data.owner).subscribe(
            (user: User) => {
              this.user = user;
            },
            (error: any) => {
              console.error('Error loading user:', error);
            }
          )
          this.userService.getUserImageById(data.owner).subscribe(
            (userImg: Blob) => {
              this.userImageUrl = URL.createObjectURL(userImg);
              console.log(userImg);
              console.log(URL.createObjectURL(userImg));
            },
            (error: any) => {
              console.error('Error loading user:', error);
            }
          )
        },
        (error: any) => {
          console.error('Error loading product:', error);
        }
      )
      this.productService.getImageById(id).subscribe(
        (image: Blob) => {
          this.imageUrl = URL.createObjectURL(image);
        },
        (error: any) => {
          console.error('Error loading product:', error);
        }
      )
      this.productService.getSimilarProducts(id, 0, 8).subscribe(
        (products: Product[]) => {
          for (let i = 0; i < products.length; i++) {
            this.productService.getImageById(products[i].id).subscribe(
              (prodImg: Blob) => {
                products[i].imageUrl = URL.createObjectURL(prodImg);
              },
              (error: any) => {
                console.error('Error loading product:', error);
              }
            )
          }
          this.related = products;
        },
        (error: any) => {
          console.error('Error loading related products:', error);
        }
      )
    }
  }

}
