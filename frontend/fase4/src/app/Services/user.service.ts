import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../Models/user.model';
import { Product } from '../Models/product.model';
import {Review} from "../Models/review.model";


const BASE_URL = '/api/users/';
@Injectable({ providedIn: 'root' })
export class UsersService{

  constructor(private httpClient: HttpClient) { }

  getUser(id: number | string): Observable<User> {
    return this.httpClient.get(BASE_URL + id).pipe() as Observable<User>;
  }

  addUserOrUpdate(user: User) {

    if (!user.userID) {
      return this.httpClient.post(BASE_URL, user)
        .pipe(
          catchError(error => this.handleError(error))
        );// si no existe el usuario se crea uno nuevo
    } else {
      return this.httpClient.patch(BASE_URL + user.userID, user).pipe(
        catchError(error => this.handleError(error))
      ); // si ya existe el usuario se actualiza
    }
  }

  deleteUser(user: User) {
    return this.httpClient.delete(BASE_URL + user.userID).pipe(
      catchError(error => this.handleError(error))
    );
  }

  getUserProductsById(id: number | string): Observable<Product[]> {
    return this.httpClient.get(BASE_URL + id +'/allProducts').pipe(
      //catchError(error => this.handleError(error))
    ) as Observable<Product[]>;
  }
  getUserRewiewsById(id: number | string): Observable<Review[]> {
    return this.httpClient.get(BASE_URL + id +'/reviews').pipe(
      //catchError(error => this.handleError(error))
    ) as Observable<Review[]>;
  }

  getUserImageById(id: number | string): Observable<Blob>{
    return this.httpClient.get(BASE_URL + id +'/image').pipe(
      //catchError(error => this.handleError(error))
    ) as Observable<Blob>;
  }
  private handleError(error: any) {
    console.error(error);
    return throwError("Server error (" + error.status + "): " + error.text())
  }
}
