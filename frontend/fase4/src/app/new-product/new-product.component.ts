import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent implements OnInit {
  formulario: FormGroup;

  constructor(private router: Router,private formBuilder: FormBuilder) {
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
  }

  registerProduct(){
    if (this.formulario.valid) {
      console.log("Formulario válido, enviar datos:", this.formulario.value);
      //this.router.navigate(['/'])
    } else {
      console.log("Formulario inválido, revisa los campos.");
    }
  }
}
