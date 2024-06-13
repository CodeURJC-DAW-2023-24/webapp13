import { Component, OnInit } from '@angular/core';
import { Report } from '../Models/report.model';
import { ReportService } from '../Services/report.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UsersService } from '../Services/user.service';

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [],
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent implements OnInit {

  report!: Report;

  constructor(private reportService: ReportService, private route: ActivatedRoute, private router: Router, private userService: UsersService) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.reportService.getReportById(id).subscribe(
      (data: Report) => {
        this.report = data;
      },
      (error: any) => {
        console.log(error);
        this.router.navigate(['/error']);
      }
    )
  }

  async delete(): Promise<void>{
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.userService.deleteUser(this.report.userReported).subscribe(
      () => {},
      (error: any) => {
        console.log(error);
      }
    );
    await this.wait(0.1);
    this.reportService.deleteReport(id).subscribe(
      () => {},
      (error: any) => {
        console.log(error);
      }
    );
    await this.wait(0.1);
    this.router.navigate(['/admin']);
  }

  async mantain(): Promise<void>{
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.reportService.deleteReport(id).subscribe(
      () => {},
      (error: any) => {
        console.log(error);
      }
    );
    await this.wait(0.1);
    this.router.navigate(['/admin']);
  }

  wait(seconds: number): Promise<void> {
    return new Promise(resolve => {
      setTimeout(resolve, seconds * 1000);
    });
  }

}
