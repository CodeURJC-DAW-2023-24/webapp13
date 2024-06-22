import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders,HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Product } from '../Models/product.model';
import { NewProduct } from '../new-product/new-product.component';
/*import { NewProduct } from '../new-product/new-product.component';*/

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  BASE_URL = '/api/products/';

  constructor(private httpClient: HttpClient) { }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error);
    return throwError(() => new Error(`Server error (${error.status}): ${error.message}`));
  }

  getAllProducts(page: number = 1, size: number = 8, productType: number = 0): Observable<Product[]> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('productType', productType.toString());

    return this.httpClient.get<Product[]>(this.BASE_URL, { params }).pipe(
      catchError(this.handleError)
    );
  }

  /*registerProduct(product: NewProduct){
    const productJson = JSON.stringify(product);
    return this.httpClient.post("https://localhost:8443/api/products/", productJson)
      .pipe(
        catchError((error: any) => this.handleError(error))
      );
  }*/
  /*
  registerProduct(product: Product): {
    return this.httpClient.post<Product>(this.BASE_URL, product).pipe(
      catchError(this.handleError)
    );
  }
  */

  registerProduct(product: NewProduct) {
    debugger;
    const body = {
      title: product.title ?? '',
      address: product.address ?? '',
      price: Number(product.price ?? 0),
      description: product.description ?? '',
      productType: Number(product.productType ?? 0),
      owner: Number(product.owner)
    };
    debugger;
    // Agregar la imagen al formData si está presente en el modelo de datos User
    return this.httpClient.post<any>(this.BASE_URL, body)
      .pipe(
        map(response => {
          // Aquí asumimos que la respuesta contiene el id del producto creado
          const productId = response.id;
          return this.httpClient.post(this.BASE_URL + productId + '/image', product.imageFile)
        }),
        catchError((error: any) => this.handleError(error))
      );
  }

  getProductById(id: number): Observable<Product> {
    const url = `${this.BASE_URL}${id}`;
    return this.httpClient.get<Product>(url).pipe(
      catchError(this.handleError)
    );
  }

  getProductsByType(id: number): Observable<Product> {
    const url = `${this.BASE_URL}type/${id}`;
    return this.httpClient.get<Product>(url).pipe(
      catchError(this.handleError)
    );
  }

  deleteProduct(id: number): Observable<void> {
    const url = `${this.BASE_URL}${id}`;
    return this.httpClient.delete<void>(url).pipe(
      catchError(this.handleError)
    );
  }

  getImageById(id: number): Observable<Blob> {
    const url = `${this.BASE_URL}${id}/image`;
    return this.httpClient.get(url, { responseType: 'blob' }).pipe(
      catchError(this.handleError)
    );
  }

  uploadImage(id: number, image: File): Observable<void> {
    const url = `${this.BASE_URL}${id}/image`;
    const formData: FormData = new FormData();
    formData.append('image', image, image.name);
    return this.httpClient.post<void>(url, formData).pipe(
      catchError(this.handleError)
    );
  }

  getSimilarProducts(id: number, page: number, pageSize: number): Observable<Product[]> {
    const url = `${this.BASE_URL}${id}/similar?page=${page}&pageSize=${pageSize}`;
    return this.httpClient.get<Product[]>(url).pipe(
      catchError(this.handleError)
    );
  }

  searchProducts(search: string, page: number, size: number): Observable<Product[]> {
    const url = `${this.BASE_URL}search?search=${search}&page=${page}&size=${size}`;
    return this.httpClient.get<Product[]>(url).pipe(
      catchError(this.handleError)
    );
  }

  getAllCategories(): Observable<string[]> {
    return this.httpClient.get<string[]>(this.BASE_URL+"categories").pipe(
      catchError(this.handleError)
    );
  }

  purchaseProduct(productID: number, rating: number = 0): Observable<string> {
    const url = `${this.BASE_URL}purchase`;
    const params = { productID: productID.toString(), rating: rating.toString() };
    return this.httpClient.post<string>(url, {}, { params }).pipe(
      catchError(this.handleError)
    );
  }

  getPdf(productID: number): Observable<Blob> {
    const url = `${this.BASE_URL}pdf/${productID}`;
    return this.httpClient.get(url, { responseType: 'blob' }).pipe(
      catchError(this.handleError)
    );
  }
}
