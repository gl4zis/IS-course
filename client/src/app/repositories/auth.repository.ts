import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {LoginReq} from '../models/auth/login.model';
import {Observable} from 'rxjs';
import {RegisterReq} from '../models/auth/register.model';
import {PasswordChangeReq} from '../models/auth/password-change.request';
import {OneFieldResponse} from '../models/one-field.response';
import {Profile} from '../models/auth/profile.model';

@Injectable({
  providedIn: 'root'
})
export class AuthRepository {
  private api = `${environment.api}/auth`;

  constructor(private http: HttpClient) {}

  register(req: RegisterReq): Observable<OneFieldResponse<string>> {
    return this.http.post<OneFieldResponse<string>>(`${this.api}/register`, req);
  }

  login(req: LoginReq): Observable<OneFieldResponse<string>> {
    return this.http.post<OneFieldResponse<string>>(`${this.api}/login`, req);
  }

  registerOther(req: RegisterReq): Observable<void> {
    return this.http.post<void>(`${this.api}/register-other`, req);
  }

  changePassword(req: PasswordChangeReq): Observable<void> {
    return this.http.post<void>(`${this.api}/change-password`, req);
  }

  getProfile(): Observable<Profile> {
    return this.http.get<Profile>(`${this.api}/profile`);
  }
}
