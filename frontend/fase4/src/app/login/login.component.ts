import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UsersService } from '../Services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
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
}
