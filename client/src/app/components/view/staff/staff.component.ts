import {Component} from '@angular/core';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';

@Component({
  selector: 'staff-view',
  standalone: true,
  templateUrl: './staff.component.html',
  imports: [
    NavHeaderComponent
  ]
})
export class StaffComponent {}
