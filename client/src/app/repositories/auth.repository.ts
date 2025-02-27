import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {LoginReq} from '../models/auth/login.model';
import {Observable} from 'rxjs';
import {JwtResponse} from '../models/auth/jwt.response';
import {RegisterReq} from '../models/auth/register.model';
import {PasswordChangeReq} from '../models/auth/password-change.request';

@Injectable({
  providedIn: 'root'
})
export class AuthRepository {
  private api = `${environment.api}/auth`;

  constructor(private http: HttpClient) {}

  register(req: RegisterReq): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.api}/register`, req);
  }

  login(req: LoginReq): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.api}/login`, req);
  }

  registerOther(req: RegisterReq): Observable<void> {
    return this.http.post<void>(`${this.api}/register-other`, req);
  }

  changePassword(req: PasswordChangeReq): Observable<void> {
    return this.http.post<void>(`${this.api}/change-password`, req);
  }
}
