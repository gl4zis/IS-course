import {Component, EventEmitter, Input, Output} from '@angular/core';
import {User} from '../../../../models/user/user.model';
import {Card} from 'primeng/card';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {Resident} from '../../../../models/user/resident.model'
import {BidStatus, BidType} from '../../../../models/bid/bid.model';

@Component({
  selector: 'bid-card',
  standalone: true,
  templateUrl: './bid-card.component.html',
  styleUrl: './bid-card.component.css',
  imports: [
    Card,
    NgIf,
    RouterLink
  ]
})
export class BidCardComponent {
  protected readonly typeMap = {
    [BidType.OCCUPATION]: 'заселение',
    [BidType.EVICTION]: 'выселение',
    [BidType.DEPARTURE]: 'отъезд',
    [BidType.ROOM_CHANGE]: 'смену комнаты'
  };

  @Input() number!: number;
  @Input() sender?: User;
  @Input() type!: BidType;
  @Input() status!: BidStatus;

  @Output() open = new EventEmitter<number>();

  isSenderResident(): boolean {
    return (this.sender as Resident).university !== undefined;
  }
}
