import { Component, OnInit } from '@angular/core';
import { FormsModule, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UsersService } from '../Services/user.service';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  user:any;
  loginObj: Login;
  loginURL = '/api/auth/login';

  constructor(private http:HttpClient, private router: Router, private authService: AuthService){
    this.loginObj = new Login();
  }

  onLogin() {
    this.http.post(this.loginURL, this.loginObj, { observe: 'response' }).subscribe((res:any)=>{
      if(!res.error) {
        alert('Login success');
        localStorage.setItem('Token', res.body.accessToken.tokenValue);
        this.user = this.authService.getUserToken();
        debugger;
        if(this.user.auth[0].authority == 'ROLE_ROLE_ADMIN') {
          this.router.navigateByUrl('/report')
        } else {
          this.router.navigateByUrl('/profile');
        }
      } else {
        alert(res.message)
      }
    })
  }

  /*
  private getTokenFromResponse(response: any): string | null {
    const authTokenCookie = response.headers.get('Set-Cookie');
    if (authTokenCookie) {
      const authTokenMatch = authTokenCookie.match(/AuthToken=([^;]+)/);
      if (authTokenMatch) {
        return authTokenMatch[1];
      }
    }
    return null;
  }
  */
}

export class Login {
  username:string;
  password:string;
  constructor(){
    this.username = "";
    this.password = "";
  }
}


/*
export class LoginComponent implements OnInit {
  admin = true;
  formulario: UntypedFormGroup;

  constructor(private router: Router,private formBuilder: UntypedFormBuilder, private usersService: UsersService) {
    this.formulario = this.formBuilder.group({
      Username: ['', Validators.required],
      Password: ['', Validators.required],
    });
  }
  ngOnInit(): void {
  }

  redirectLogin(){
    if (this.formulario.valid) {
      console.log("Formulario válido, enviar datos:", this.formulario.value);
      this.usersService.login(this.formulario.get('Username')?.value, this.formulario.get('Password')?.value).subscribe(
        (response: any) => {
          // Manejar la respuesta exitosa
          console.log(response);
          if (response && response.status === "SUCCESS") {
            // El inicio de sesión fue exitoso
            console.log("Inicio de sesión exitoso");
            // Puedes redirigir al usuario a otra página o realizar otras acciones
          } else {
            // El servidor devolvió un código de estado diferente de 200
            console.log("usuario y/o contraseña incorrectos");
            // Puedes mostrar un mensaje de error al usuario o realizar otras acciones
          }
        },
      );
    } else {
      console.log("Formulario inválido, revisa los campos.");
    }
  }

  getUserInfo(){
    this.usersService.getUserInfo().subscribe(
      (userInfo: string) => {
        console.log(userInfo); // Aquí tendrás acceso al nombre de usuario como un String
      },
      (error) => {
        console.error('Hubo un error:', error);
      }
    );
    console.log(this.usersService.isLoggedIn());
  }

  logout(){
    this.usersService.logout();
  }

  getCookies(){
    console.log(this.usersService.getCookies());
  }

  password(username: any, password: any) {
    throw new Error('Method not implemented.');
  }
}*/
