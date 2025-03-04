import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environment/environment';
import {Observable} from 'rxjs';
import {Resident} from '../models/user/resident.model';
import {User} from '../models/user/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserRepository {
  private api = `${environment.api}/user`;

  constructor(private http: HttpClient) { }

  getStaff(): Observable<User[]> {
    return this.http.get<User[]>(`${this.api}/staff`);
  }

  getResidents(): Observable<Resident[]> {
    return this.http.get<Resident[]>(`${this.api}/residents`);
  }

  fire(login: string): Observable<void> {
    return this.http.delete<void>(`${this.api}/fire?login=${login}`, {});
  }
}
