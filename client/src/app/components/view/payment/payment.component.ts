import {Component} from '@angular/core';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';

@Component({
  selector: 'payment-view',
  standalone: true,
  templateUrl: './payment.component.html',
  imports: [
    NavHeaderComponent
  ]
})
export class PaymentComponent {}
