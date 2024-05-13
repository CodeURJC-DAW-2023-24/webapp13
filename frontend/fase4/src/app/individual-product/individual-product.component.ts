import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-individual-product',
  templateUrl: './individual-product.component.html',
  styleUrls: ['./individual-product.component.css']
})
export class IndividualProductComponent implements OnInit {
  formulario: FormGroup;

  constructor(private router: Router,private formBuilder: FormBuilder) {
    this.formulario = this.formBuilder.group({
      ProductID: ['', Validators.required],
    });
  }

  ngOnInit(): void {
  }

  getProduct(){
    //this.router.navigate(['/product/id'])
  }

}
