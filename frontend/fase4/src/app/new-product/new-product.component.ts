import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Product } from '../Models/product.model';
import { UsersService } from '../Services/user.service';
import { User } from '../Models/user.model';
import { Observable } from 'rxjs';
import { ProductService } from '../Services/product.service';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent implements OnInit {
  formulario: UntypedFormGroup;
  newProduct?: NewProduct;
  username:any;
  userId?: number;
  user: any;

  constructor(private router: Router, private formBuilder: UntypedFormBuilder, private userService: UsersService, private productService: ProductService, private authService: AuthService) {
    this.formulario = this.formBuilder.group({
      Title: ['', Validators.required],
      imageFile: ['', Validators.required],
      Price: ['', Validators.required],
      Description: ['', Validators.required],
      Category: ['', Validators.required],
      Address: ['', Validators.required],
      City: ['', Validators.required],
      Province: ['', Validators.required],
      Cp: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.user = this.authService.getUserToken();
        if(this.user.auth[0].authority != 'ROLE_ROLE_USER') {
          this.router.navigate(['/']);
        }
      }
      else {
        this.router.navigate(['/']);
      }
    this.username = this.userService.getUserInfo();
    this.userService.getUserIdByUsername(this.username).subscribe(
      (id: number) => {
        this.userId = id;
      },
      (error: any) => {
        console.error('Error fetching user ID:', error);
      }
    );
  }

  private productNumberDict: { [key: string]: number } = {
    'Electrónica': 1,
    'Muebles': 2,
    'Ropa': 3,
    'Libros': 4,
    'Deportes': 5,
    'Hogar y Jardín': 6,
    'Juguetes': 7
};

  getProductNumberFromDict(label: string): number {
      const number = this.productNumberDict[label];
      if (number !== undefined) {
          return number;
      } else {
          throw new Error('Etiqueta desconocida');
      }
  }

  registerProduct(){
    if (this.formulario.valid && this.userId) {
      debugger;
      console.log("Formulario válido, enviar datos:", this.formulario.value);
      this.newProduct = new NewProduct(this.formulario.value.Title, this.formulario.value.Address, this.formulario.value.Price, this.formulario.value.Description,
        this.formulario.value.imageFile !== null, this.getProductNumberFromDict(this.formulario.value.Category), this.userId, this.formulario.value.imageFile);
      this.productService.registerProduct(this.newProduct)
      this.router.navigate(['/'])
    } else {
      console.log("Formulario inválido, revisa los campos.");
    }
  }

  /*
  async registerProduct(): Promise<void>{
    if (this.formulario.valid && this.userId) {
      debugger;
      console.log("Formulario válido, enviar datos:", this.formulario.value);
      this.newProduct = new NewProduct(this.formulario.value.Title, this.formulario.value.Address, this.formulario.value.Price, this.formulario.value.Description,
        this.formulario.value.imageFile !== null, this.getProductNumberFromDict(this.formulario.value.Category), this.userId);
      this.productService.registerProduct(this.newProduct).subscribe(
        (        response: any) => {
          console.log('Producto registrado exitosamente:', response);
          this.router.navigate(['/'])
        },
        (        error: any) => {
          console.error('Error al registrar el producto:', error);
        }
      );
      await this.wait(0.1);
    } else {
      console.log("Formulario inválido, revisa los campos.");
    }
  }

  wait(seconds: number): Promise<void> {
    return new Promise(resolve => {
      setTimeout(resolve, seconds * 1000);
    });
  }
  */
}

export class NewProduct{
  title: string;
  address: string;
  price: number;
  description: string;
  image: boolean;
  productType: number;
  owner: number;
  imageFile: Blob;
  constructor(title:string, address: string, price: number, description: string, image: boolean, productType: number, owner: number, imageFile: Blob){
    this.title = title;
    this.address = address;
    this.price = price;
    this.description = description;
    this.image = image;
    this.productType = productType;
    this.owner = owner;
    this.imageFile = imageFile;
  }
}
