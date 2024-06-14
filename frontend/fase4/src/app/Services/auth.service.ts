import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) { }

  getToken(): string | null {
    return localStorage.getItem('Token');
  }

  getUserToken() {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken;
    }
    return null;
  }

  logout() {
    localStorage.removeItem('Token');
    this.router.navigate(['/']);
  }
}
