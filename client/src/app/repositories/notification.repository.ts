import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Notification} from '../models/notification/notification.model';

@Injectable({
  providedIn: 'root'
})
export class NotificationRepository {
  private api = `${environment.api}/notification`;

  constructor(private http: HttpClient) {}

  getNewNotifications(): Observable<Notification[]> {
    return this.http.get<Notification[]>(`${this.api}/new`);
  }
}
