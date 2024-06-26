import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { UsersService } from '../Services/user.service';
import { User } from '../Models/user.model';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  formulario: UntypedFormGroup;
  newUser: User = new User();
  user: any;
  passwordMissmatch: Boolean = false
  usernameUsed: Boolean = false;

  constructor(private router: Router,private formBuilder: UntypedFormBuilder, private usersService: UsersService, private authService: AuthService) {
    this.formulario = this.formBuilder.group({
      Name: ['', Validators.required],
      Username: ['', Validators.required],
      Email: ['', [Validators.required, Validators.email]],
      Password: ['', Validators.required],
      RepeatPassword: ['', Validators.required],
      Image: ['',],
    }, {
      validators: this.passwordMatchValidator
    });
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
        this.router.navigate(['/']);
      }
  }

  passwordMatchValidator(form: UntypedFormGroup) {
    const password = form.get('Password');
    const repeatPassword = form.get('RepeatPassword');

    if (password && repeatPassword && password.value !== repeatPassword.value) {
      repeatPassword.setErrors({ passwordMismatch: true });
    } else {
      repeatPassword?.setErrors(null);
    }
  }

  registerUser(){
    if (this.formulario.valid) {
      console.log("Formulario válido, enviar datos:", this.formulario.value);
      this.newUser = new User(0,0,['USER'],[],0,this.formulario.value.Username,this.formulario.value.Password, this.formulario.value.Name, this.formulario.value.Email, this.formulario.value.Image);
      this.usersService.addUserOrUpdate(this.newUser).subscribe(
        (        response: any) => {
          console.log('Usuario registrado exitosamente:', response);
          // Navega a la página de login después de registrar el usuario
          this.router.navigate(['/login']);
        },
        (        error: any) => {
          console.error('Error al registrar el usuario:', error);
          // Manejo de errores adicional si es necesario
        }
      );
      this.usersService.getUserByUsername(this.formulario.value.Username).subscribe(
        ()=> this.usernameUsed = true
      );
      //this.router.navigate(['/login'])
    } else {
      console.log("Formulario inválido, revisa los campos.");
      if (this.formulario.value.Password != this.formulario.value.RepeatPassword){
        this.passwordMissmatch = true;
      }
    }
  }

}
