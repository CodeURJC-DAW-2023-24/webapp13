import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { UsersService } from '../Services/user.service';
import { User } from '../Models/user.model';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  formulario: UntypedFormGroup;
  newUser: User = new User();

  constructor(private router: Router,private formBuilder: UntypedFormBuilder, private usersService: UsersService) {
    this.formulario = this.formBuilder.group({
      Name: ['', Validators.required],
      Username: ['', Validators.required],
      Email: ['', [Validators.required, Validators.email]],
      Password: ['', Validators.required],
      RepeatPassword: ['', Validators.required],
      Image: ['', Validators.required],
    }, {
      validators: this.passwordMatchValidator
    });
  }

  ngOnInit(): void {
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
      this.newUser = new User(
        this.formulario.value.Username,
        this.formulario.value.Image,
        this.formulario.value.Email,
        this.formulario.value.Password,
        this.formulario.value.Name,
        '', // Asume que reviewList empieza vacío
        "USER"// Asume que roles se envían como un array separado por comas si hay roles en el formulario
        // ...this.formulario.value.roles ? this.formulario.value.roles.split(',') : []
      );
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
      //this.router.navigate(['/login'])
    } else {
      console.log("Formulario inválido, revisa los campos.");
    }
  }

}
