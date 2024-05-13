import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-report-form',
  templateUrl: './report-form.component.html',
  styleUrls: ['./report-form.component.css']
})
export class ReportFormComponent implements OnInit {
  formulario: FormGroup;

  constructor(private router: Router,private formBuilder: FormBuilder) {
    this.formulario = this.formBuilder.group({
      Category: ['', Validators.required],
      Description: ['', Validators.required],
    });
  }

  ngOnInit(): void {
  }

  addReport(){
    if (this.formulario.valid) {
      console.log("Formulario válido, enviar datos:", this.formulario.value);
      //this.router.navigate(['/'])
    } else {
      console.log("Formulario inválido, revisa los campos.");
    }
  }
}
