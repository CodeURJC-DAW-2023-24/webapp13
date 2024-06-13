import { Component, OnInit } from '@angular/core';
import { Report } from '../Models/report.model';
import { ReportService } from '../Services/report.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {
  reports!: Report[];

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.reportService.getReports().subscribe(
      (data: Report[]) => {
        this.reports = data;
      }
    )
  }

}
