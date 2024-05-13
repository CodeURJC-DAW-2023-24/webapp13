import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  formulario: FormGroup;

  constructor(private router: Router,private formBuilder: FormBuilder) {
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

  passwordMatchValidator(form: FormGroup) {
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
      //this.router.navigate(['/login'])
    } else {
      console.log("Formulario inválido, revisa los campos.");
    }
  }

}
