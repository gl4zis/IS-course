import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Toast} from 'primeng/toast';
import {ConfirmDialog} from 'primeng/confirmdialog';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Toast, ConfirmDialog],
  templateUrl: './app.component.html'
})
export class AppComponent {
}
