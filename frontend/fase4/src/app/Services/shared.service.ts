import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private categorySubject = new Subject<number>();

  categoryChanged$ = this.categorySubject.asObservable();

  changeCategory(index: number): void {
    this.categorySubject.next(index);
  }
}
