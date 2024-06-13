import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {Report} from "../Models/report.model";
import {User} from "../Models/user.model";

const BASE_URL = '/api/reports/';

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


  addReport(user: string, description: string, category: string) {
    const formData = new FormData();
    formData.append('userReported', user);
    formData.append('description', description);
    formData.append('category', category);
    return this.httpClient.post(BASE_URL, formData)
      .pipe(
        catchError(error => this.handleError(error))
      );
  }

  deleteReport(id: number) {
    return this.httpClient.delete(BASE_URL + id).pipe(
      catchError(error => this.handleError(error))
    );
  }


  private handleError(error: HttpErrorResponse): Observable<Object> {
    console.log("ERROR:");
    console.error(error);
    return throwError(`Server error (${error.status}): ${error.message}`);
  }
}
