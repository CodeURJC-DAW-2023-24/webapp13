import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../Models/user.model';
import { UsersService } from '../Services/user.service';
import { Product } from '../Models/product.model';
import { ProductService } from '../Services/product.service';
import { Review } from '../Models/review.model';

@Component({
  selector: 'app-profile-consult',
  templateUrl: './profile-consult.component.html',
  styleUrls: ['./profile-consult.component.css']
})
export class ProfileConsultComponent implements OnInit {

  user!: User;
  products!: Product[];
  rating: number = 0;
  imgUrl!: string;

  constructor(private router: Router, private route: ActivatedRoute, private userService: UsersService, private productService: ProductService) { }

  ngOnInit(): void {
    this.getInfo();
  }

  getInfo(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id){
      this.userService.getUser(id).subscribe(
        (user: User) => {
          this.user = user;
        }
      )
      this.userService.getUserProductsById(id).subscribe(
        (data: Product[]) => {
          for (let i = 0; i < data.length; i++) {
            this.productService.getImageById(data[i].id).subscribe(
              (prodImg: Blob) => {
                data[i].imageUrl = URL.createObjectURL(prodImg);
              },
              (error: any) => {
                console.error('Error loading product:', error);
              }
            )
          }
          this.products = data;
        }
      )
      this.userService.getUserRewiewsById(id).subscribe(
        (list: Review[]) => {
          let totalRating = 0;

          // Suma todos los ratings
          list.forEach(review => {
            totalRating += review.rating;
          });

          this.rating = totalRating / list.length
        }
      )
      this.userService.getUserImageById(id).subscribe(
        (userImg: Blob) => {
          this.imgUrl = URL.createObjectURL(userImg);
        },
        (error: any) => {
          console.error('Error loading user:', error);
        }
      );
    }
  }

}
