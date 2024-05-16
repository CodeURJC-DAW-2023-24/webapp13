import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-report-form',
  templateUrl: './report-form.component.html',
  styleUrls: ['./report-form.component.css']
})
export class ReportFormComponent implements OnInit {
  formulario: UntypedFormGroup;

  constructor(private router: Router,private formBuilder: UntypedFormBuilder) {
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
