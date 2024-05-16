import { Component, OnInit } from '@angular/core';
import { ProductType } from '../Models/product-type.model';
import { ProductService } from '../Services/product.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  constructor() {}

  ngOnInit(): void {
  }
}
