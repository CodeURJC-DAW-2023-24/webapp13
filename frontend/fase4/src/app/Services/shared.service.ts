import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private categorySubject = new Subject<number>();
  private searchSubject = new Subject<string>();

  categoryChanged$ = this.categorySubject.asObservable();
  searchChanged$ = this.searchSubject.asObservable();

  changeCategory(index: number): void {
    this.categorySubject.next(index);
  }

  changeSearchTerm(term: string): void {
    this.searchSubject.next(term);
  }
}
