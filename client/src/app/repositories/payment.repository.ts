import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DebtResponse} from '../models/payment/debt.response';
import {PaymentRequest} from '../models/payment/payment.request';

@Injectable({
  providedIn: 'root'
})
export class PaymentRepository {
  private api = `${environment.api}/payment`;

  constructor(private http: HttpClient) {}

  getDebt(): Observable<DebtResponse> {
    return this.http.get<DebtResponse>(`${this.api}/debt`);
  }
  
  pay(req: PaymentRequest): Observable<void> {
    return this.http.post<void>(`${this.api}/pay`, req);
  }
}
