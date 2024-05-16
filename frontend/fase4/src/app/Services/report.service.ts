import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {Report} from "../Models/report.model";
import {User} from "../Models/user.model";

const BASE_URL = '/api/reports';

@Injectable({ providedIn: 'root' })
export class ReportService{
  constructor(private httpClient: HttpClient) { }


  getReports(): Observable<Report[]> {
    return this.httpClient.get<Report[]>(BASE_URL).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Report[]>;
  }

  getReportById(id: number | string): Observable<Report> {
    return this.httpClient.get<Report>(BASE_URL + id).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Report>;
  }


  addReport(report: Report) {
    return this.httpClient.post(BASE_URL, report)
      .pipe(
        catchError(error => this.handleError(error))
      );
  }

  deleteUReport(report: Report) {
    return this.httpClient.delete(BASE_URL + report.id).pipe(
      catchError(error => this.handleError(error))
    );
  }


  private handleError(error: HttpErrorResponse): Observable<Object> {
    console.log("ERROR:");
    console.error(error);
    return throwError(`Server error (${error.status}): ${error.message}`);
  }
}
