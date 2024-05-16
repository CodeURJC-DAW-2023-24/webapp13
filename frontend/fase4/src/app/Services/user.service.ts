import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { User } from '../Models/user.model';
import { Product } from '../Models/product.model';
import {Rewiew} from "../Models/rewiew.model";
import { CookieService } from 'ngx-cookie-service';


const BASE_URL = '/api/users/';
@Injectable({ providedIn: 'root' })
export class UsersService{
  static login(username: any, password: any) {
    throw new Error('Method not implemented.');
  }

  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }

  getUser(id: number | string): Observable<User> {
    return this.httpClient.get(BASE_URL + id).pipe(
      //catchError(error => this.handleError(error))
    ) as Observable<User>;
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
  getUserRewiewsById(id: number | string): Observable<Rewiew[]> {
    return this.httpClient.get(BASE_URL + id +'/reviews').pipe(
      //catchError(error => this.handleError(error))
    ) as Observable<Rewiew[]>;
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

  login(username: string, password: string): Observable<any> {
    const body = {
      username: username,
      password: password
    };

    return this.httpClient.post<any>('https://localhost:8443/api/auth/login', body)
      .pipe(
        tap(response => {
          // Manejar la respuesta del servidor aquí
          // Por ejemplo, puedes guardar el token en el almacenamiento local o en una cookie
          // Aquí asumiré que el servidor devuelve un token de acceso en la respuesta

          const authToken = response.AuthToken;
          const refreshToken = response.RefreshToken;

          // Guardar las cookies en el almacenamiento local
          this.cookieService.set('authToken', authToken);
          this.cookieService.set('refreshToken', refreshToken);
        }),
        catchError(error => {
          // Manejar errores aquí
          console.log(error);
          return this.handleError(error);
        })
      );
  }

  getUserInfo(): Observable<string> {
    // Obtener el token de autenticación del almacenamiento local
    const authToken = localStorage.getItem('authToken');
    
    // Añadir el token de autenticación a la cabecera de la solicitud
    const headers = {
      'Authorization': `Bearer ${authToken}`
    };
  
    // Realizar la solicitud con las cabeceras adecuadas
    return this.httpClient.get('https://localhost:8443/api/auth/userInfo', { headers, responseType: 'text' });
  }

  getCookies(): { authToken: string | null, refreshToken: string | null } {
    const authToken = this.cookieService.get('authToken');
    const refreshToken = this.cookieService.get('refreshToken');
    
    return { authToken, refreshToken };
  }
  
  isLoggedIn(): boolean {
    // Verificar si el token de autenticación está presente en las cookies
    return !!this.cookieService.get('authToken');
  }
  
  logout(): void {
    // Limpiar las cookies al cerrar sesión
    this.cookieService.delete('authToken');
    this.cookieService.delete('refreshToken');
  }
}
