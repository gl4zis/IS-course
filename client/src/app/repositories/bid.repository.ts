import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Bid} from '../models/bid/bid.model';
import {BidRequest, DepartureRequest, OccupationRequest, RoomChangeRequest} from '../models/bid/bid.request';

@Injectable({
  providedIn: 'root'
})
export class BidRepository {
  private api = `${environment.api}/bid`;

  constructor(private http: HttpClient) {}

  getSelf(): Observable<Bid[]> {
    return this.http.get<Bid[]>(`${this.api}/my`);
  }

  get(id: number): Observable<Bid> {
    return this.http.get<Bid>(`${this.api}/${id}`);
  }

  getInProcess(): Observable<Bid[]> {
    return this.http.get<Bid[]>(`${this.api}/in-process`);
  }

  getPending(): Observable<Bid[]> {
    return this.http.get<Bid[]>(`${this.api}/pending`);
  }

  getArchived(): Observable<Bid[]> {
    return this.http.get<Bid[]>(`${this.api}/archived`);
  }

  createOccupation(req: OccupationRequest): Observable<void> {
    return this.http.post<void>(`${this.api}/occupation`, req);
  }

  editOccupation(id: number, req: OccupationRequest): Observable<void> {
    return this.http.put<void>(`${this.api}/occupation/${id}`, req);
  }

  createEviction(req: BidRequest): Observable<void> {
    return this.http.post<void>(`${this.api}/eviction`, req);
  }

  editEviction(id: number, req: BidRequest): Observable<void> {
    return this.http.put<void>(`${this.api}/eviction/${id}`, req);
  }

  createDeparture(req: DepartureRequest): Observable<void> {
    return this.http.post<void>(`${this.api}/departure`, req);
  }

  editDeparture(id: number, req: DepartureRequest): Observable<void> {
    return this.http.put<void>(`${this.api}/departure/${id}`, req);
  }

  createRoomChange(req: RoomChangeRequest): Observable<void> {
    return this.http.post<void>(`${this.api}/room-change`, req);
  }

  editRoomChange(id: number, req: RoomChangeRequest): Observable<void> {
    return this.http.put<void>(`${this.api}/room-change/${id}`, req);
  }

  deny(id: number, comment: string): Observable<void> {
    return this.http.post<void>(`${this.api}/${id}/deny`, { data: comment });
  }

  accept(id: number): Observable<void> {
    return this.http.post<void>(`${this.api}/${id}/accept`, {});
  }

  pend(id: number, comment: string): Observable<void> {
    return this.http.post<void>(`${this.api}/${id}/pend`, { data: comment });
  }
}
