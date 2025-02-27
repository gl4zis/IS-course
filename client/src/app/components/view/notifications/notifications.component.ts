import {Component} from '@angular/core';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';

@Component({
  selector: 'notifications-view',
  standalone: true,
  imports: [
    NavHeaderComponent
  ],
  templateUrl: './notifications.component.html'
})
export class NotificationsComponent {

}
