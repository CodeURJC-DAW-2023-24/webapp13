import { Component, OnInit } from '@angular/core';
import { Report } from '../Models/report.model';
import { ReportService } from '../Services/report.service';
import { AuthService } from '../Services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {
  reports!: Report[];
  user: any;

  constructor(private reportService: ReportService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.user = this.authService.getUserToken();
        if(this.user.auth[0].authority != 'ROLE_ROLE_ADMIN') {
          this.router.navigate(['/']);
        }
      }
      else {
        this.router.navigate(['/']);
      }
    this.reportService.getReports().subscribe(
      (data: Report[]) => {
        this.reports = data;
      }
    )
  }

  onLogout(): void{
    this.authService.logout();
  }

}
