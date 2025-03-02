import {Component} from '@angular/core';
import {GuardRepository} from '../../../repositories/guard.repository';
import {ToastService} from '../../../services/toast.service';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';
import {Card} from 'primeng/card';
import {PrimeTemplate} from 'primeng/api';
import {FloatLabel} from 'primeng/floatlabel';
import {FormsModule} from '@angular/forms';
import {InputText} from 'primeng/inputtext';
import {Button} from 'primeng/button';

@Component({
  selector: 'inout-view',
  standalone: true,
  imports: [
    NavHeaderComponent,
    Card,
    PrimeTemplate,
    FloatLabel,
    FormsModule,
    InputText,
    Button
  ],
  templateUrl: './inout.component.html'
})
export class InoutComponent {
  login: string = "";

  constructor(
    private guardRepository: GuardRepository,
    private toast: ToastService
  ) {}

  entry(): void {
    if (this.login && this.login.length) {
      this.guardRepository.entry(this.login).subscribe({
        next: () => this.toast.success("Entry was successfully registered"),
      });
    }
    this.login = "";
  }

  exit(): void {
    if (this.login && this.login.length) {
      this.guardRepository.exit(this.login).subscribe({
        next: () => this.toast.success("Exit was successfully registered"),
      });
    }
    this.login = "";
  }
}
