import {Component} from '@angular/core';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';

@Component({
  selector: 'residents-view',
  standalone: true,
  templateUrl: './residents.component.html',
  imports: [
    NavHeaderComponent
  ]
})
export class ResidentsComponent {}
