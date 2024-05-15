import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product } from '../Models/product.model';
import { Review } from '../Models/review.model';
import { ProductType } from '../Models/product-type.model';

const BASE_URL = '/api/products/';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) { }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error);
    return throwError(() => new Error(`Server error (${error.status}): ${error.message}`));
  }

  getAllProducts(page: number, size: number, productType: number): Observable<Product[]> {
    const url = `${BASE_URL}?page=${page}&size=${size}&productType=${productType}`;
    return this.httpClient.get<Product[]>(url).pipe(
      catchError(this.handleError)
    );
  }

  registerProduct(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(BASE_URL, product).pipe(
      catchError(this.handleError)
    );
  }

  getProductById(id: number): Observable<Product> {
    const url = `${BASE_URL}${id}`;
    return this.httpClient.get<Product>(url).pipe(
      catchError(this.handleError)
    );
  }

  deleteProduct(id: number): Observable<void> {
    const url = `${BASE_URL}${id}`;
    return this.httpClient.delete<void>(url).pipe(
      catchError(this.handleError)
    );
  }

  getImageById(id: number): Observable<Blob> {
    const url = `${BASE_URL}${id}/image`;
    return this.httpClient.get(url, { responseType: 'blob' }).pipe(
      catchError(this.handleError)
    );
  }

  uploadImage(id: number, image: File): Observable<void> {
    const url = `${BASE_URL}${id}/image`;
    const formData: FormData = new FormData();
    formData.append('image', image, image.name);
    return this.httpClient.post<void>(url, formData).pipe(
      catchError(this.handleError)
    );
  }

  getSimilarProducts(id: number, page: number, pageSize: number): Observable<Product[]> {
    const url = `${BASE_URL}${id}/similar?page=${page}&pageSize=${pageSize}`;
    return this.httpClient.get<Product[]>(url).pipe(
      catchError(this.handleError)
    );
  }

  searchProducts(search: string, page: number, size: number): Observable<Product[]> {
    const url = `${BASE_URL}search?search=${search}&page=${page}&size=${size}`;
    return this.httpClient.get<Product[]>(url).pipe(
      catchError(this.handleError)
    );
  }

  getAllCategories(): Observable<ProductType[]> {
    return this.httpClient.get<ProductType[]>("api/products/getCategories").pipe(
      catchError(this.handleError)
    );
  }

  purchaseProduct(productID: number, rating: number = 0): Observable<string> {
    const url = `${BASE_URL}purchase`;
    const params = { productID: productID.toString(), rating: rating.toString() };
    return this.httpClient.post<string>(url, {}, { params }).pipe(
      catchError(this.handleError)
    );
  }
}
