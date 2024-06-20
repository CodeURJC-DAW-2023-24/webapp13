import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../Models/user.model';
import { UsersService } from '../Services/user.service';
import { ReportService } from '../Services/report.service';
import { Report } from '../Models/report.model';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-report-form',
  templateUrl: './report-form.component.html',
  styleUrls: ['./report-form.component.css']
})
export class ReportFormComponent implements OnInit {
  formulario: UntypedFormGroup;
  user!: User;
  date: Date = new Date();
  currentUser: string = '';
  user1: any;

  constructor(private router: Router,private formBuilder: UntypedFormBuilder, private userService: UsersService, private route: ActivatedRoute, private reportService: ReportService, private authService: AuthService) {
    this.formulario = this.formBuilder.group({
      Category: ['', Validators.required],
      Description: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.user1 = this.authService.getUserToken();
        if(this.user1.auth[0].authority != 'ROLE_ROLE_USER') {
          this.router.navigate(['/']);
        }
      }
    else {
      this.router.navigate(['/']);
    }
    this.getInfo();
  }

  addReport() {
    if (this.formulario.valid) {
      console.log("Formulario válido, enviar datos:", this.formulario.value);
      this.reportService.addReport(this.user.username, this.formulario.value.Description, this.formulario.value.Category).subscribe(
        () => {
          // Aquí maneja el éxito de la operación de añadir reporte
        },
        (error: any) => {
          console.log("Error al agregar reporte:", error);
        }
      );
      // this.router.navigate(['/']);
    } else {
      console.log("Formulario inválido, revisa los campos.");
    }
    this.router.navigate(['/']);
  }

  getInfo(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.userService.getUser(id).subscribe(
      (user: User) => {
        this.user = user;
      },
      (error: any) => {
        console.log(error);
        this.router.navigate(['/error']);
      }
    )
    this.currentUser = this.userService.getUserInfo();
  }
}
