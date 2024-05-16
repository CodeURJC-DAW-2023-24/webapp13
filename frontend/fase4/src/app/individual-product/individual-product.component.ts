import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-individual-product',
  templateUrl: './individual-product.component.html',
  styleUrls: ['./individual-product.component.css']
})
export class IndividualProductComponent implements OnInit {
  formulario: UntypedFormGroup;

  constructor(private router: Router,private formBuilder: UntypedFormBuilder) {
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
