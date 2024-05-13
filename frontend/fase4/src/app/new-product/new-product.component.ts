import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent implements OnInit {

  constructor(private router: Router) {}

  ngOnInit(): void {
  }

  registerProduct(){
    this.router.navigate(['/'])
  }
}
