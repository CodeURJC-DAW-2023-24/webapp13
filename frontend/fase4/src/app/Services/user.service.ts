import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { User } from '../Models/user.model';
import { Product } from '../Models/product.model';
import { CookieService } from 'ngx-cookie-service';
import {Review} from "../Models/review.model";
import { AuthService } from './auth.service';


const BASE_URL = '/api/users/';
@Injectable({ providedIn: 'root' })
export class UsersService{
  static login(username: any, password: any) {
    throw new Error('Method not implemented.');
  }

  constructor(private httpClient: HttpClient, private authService: AuthService) { }

  getUser(id: number | string): Observable<User> {
    return this.httpClient.get<User>(BASE_URL + id).pipe(
      catchError((error: any) => this.handleError(error))
    ) as Observable<User>;
  }

  getUserByUsername(username: string): Observable<User> {
    return this.httpClient.get<User>(BASE_URL + 'username/' + username).pipe(
      catchError((error: any) => this.handleError(error))
    ) as Observable<User>;
  }

  getUserIdByUsername(username: string): Observable<number> {
    return this.httpClient.get<User>(BASE_URL + 'username/' + username).pipe(
      map((user: User) => user.userID),
      catchError((error: any) => this.handleError(error))
    ) as Observable<number>;
  }

  addUserOrUpdate(user: User) {
    const formData = new FormData();
    const fullName = user.fullName ?? '';
    const username = user.username ?? '';
    const email = user.userEmail ?? '';
    const password = user.encodedPassword ?? '';

    // Agregar campos de texto al formData

    formData.append('name', fullName);
    formData.append('username', username);
    formData.append('email', email);
    formData.append('password', password);
    formData.append('repeatPassword', password);

    // Agregar la imagen al formData si está presente en el modelo de datos User
    const image = user.userImg;
    if (image) {
      formData.append('image', image); // Asegúrate de que el nombre 'image' coincida con el nombre esperado en el backend
    }

    if (!user.userID || user.userID == 0) {
      return this.httpClient.post("https://localhost:8443/api/users/", formData)
        .pipe(
          catchError((error: any) => this.handleError(error))
        );// si no existe el usuario se crea uno nuevo
    } else {
      return this.httpClient.patch(BASE_URL + user.userID, formData).pipe(
        catchError((error: any) => this.handleError(error))
      ); // si ya existe el usuario se actualiza
    }
  }

  updateUser(user: User, currentPassword: string) {
    const formData = new FormData();
    const fullName = user.fullName ?? '';
    const username = user.username ?? '';
    const newPassword = user.encodedPassword ?? '';
    const image = user.userImg;

    formData.append('userID', fullName);
    formData.append('fullName', fullName);
    formData.append('username', username);
    formData.append('currentPassword', currentPassword);
    formData.append('newPassword', newPassword);
    formData.append('confirmPassword', newPassword);
    if (image) {
      formData.append('imageFile', image);
    }

    return this.httpClient.patch(BASE_URL + user.userID, formData).pipe(
      catchError((error: any) => this.handleError(error))
    );
  }


  deleteUser(id: number) {
    return this.httpClient.delete(BASE_URL + id).pipe(
      catchError((error: any) => this.handleError(error))
    );
  }

  getUserProductsById(id: number | string): Observable<Product[]> {
    return this.httpClient.get<Product[]>(BASE_URL + id +'/allProducts').pipe(
      catchError((error: any) => this.handleError(error))
    ) as Observable<Product[]>;
  }
  getUserRewiewsById(id: number | string): Observable<Review[]> {
    return this.httpClient.get<Review[]>(BASE_URL + id +'/reviews').pipe(
      catchError((error: any) => this.handleError(error))
    ) as Observable<Review[]>;
  }

  getUserImageById(id: number | string): Observable<Blob>{
    return this.httpClient.get<Blob>(BASE_URL + id +'/image').pipe(
      catchError((error: any) => this.handleError(error))
    ) as Observable<Blob>;
  }
  private handleError(error: any) {
    console.error(error);
    return throwError("Server error (" + error.status + "): " + error.text())
  }

  getUserInfo(): string {
    const userInfo = this.authService.getUserToken();
    return userInfo ? userInfo.sub : null;
  }

}
