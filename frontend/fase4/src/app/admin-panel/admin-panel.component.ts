import { Component, OnInit } from '@angular/core';
import { Report } from '../Models/report.model';
import { ReportService } from '../Services/report.service';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {
  reports!: Report[];

  constructor(private reportService: ReportService, private authService: AuthService) { }

  ngOnInit(): void {
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
