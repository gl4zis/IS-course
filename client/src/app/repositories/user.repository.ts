import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environment/environment';
import {Observable} from 'rxjs';
import {Staff} from '../models/user/staff.model';
import {Resident} from '../models/user/resident.model';

@Injectable({
  providedIn: 'root'
})
export class UserRepository {
  private api = `${environment.api}/user`;

  constructor(private http: HttpClient) { }

  getStaff(): Observable<Staff[]> {
    return this.http.get<Staff[]>(`${this.api}/staff`);
  }

  getResidents(): Observable<Resident[]> {
    return this.http.get<Resident[]>(`${this.api}/residents`);
  }

  fire(login: string): Observable<void> {
    return this.http.delete<void>(`${this.api}/fire?login=${login}`, {});
  }
}
