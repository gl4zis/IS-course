import {Component, OnInit} from '@angular/core';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';
import {AuthService} from '../../../services/auth.service';
import {Bid} from '../../../models/bid/bid.model';
import {BidRepository} from '../../../repositories/bid.repository';
import {Role} from '../../../models/auth/role.model';
import {Tab, TabList, TabPanel, TabPanels, Tabs} from 'primeng/tabs';
import {NgForOf, NgIf} from '@angular/common';
import {BidCardComponent} from './bid-card/bid-card.component';
import {ActivatedRoute, Router} from '@angular/router';
import {Utils} from '../../../services/utils';
import {Dialog} from 'primeng/dialog';
import {Button} from 'primeng/button';

@Component({
  selector: 'bids-view',
  standalone: true,
  templateUrl: './bids.component.html',
  imports: [
    NavHeaderComponent,
    Tabs,
    TabList,
    Tab,
    TabPanels,
    NgIf,
    TabPanel,
    BidCardComponent,
    NgForOf,
    Dialog,
    Button
  ]
})
export class BidsComponent implements OnInit {
  selfBids: Bid[] = [];

  inProcessBids: Bid[] = [];
  pendingBids: Bid[] = [];
  archivedBids: Bid[] = [];

  viewBid?: Bid;
  viewOpened = false;

  constructor(
    protected authService: AuthService,
    private bidRepository: BidRepository,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['id']) {
        this.openBid(Number(params['id']));
      }
    });
    if (this.authService.getRole() === Role.MANAGER) {
      this.loadInProcessBids();
    } else {
      this.loadSelfBids();
    }
  }

  loadSelfBids(): void {
    this.bidRepository.getSelf().subscribe({
      next: (res) => this.selfBids = res
    });
  }

  loadInProcessBids(): void {
    this.bidRepository.getInProcess().subscribe({
      next: (res) => this.inProcessBids = res
    });
  }

  loadPendingBids(): void {
    this.bidRepository.getPending().subscribe({
      next: (res) => this.pendingBids = res
    });
  }

  loadArchivedBids(): void {
    this.bidRepository.getArchived().subscribe({
      next: (res) => this.archivedBids = res
    });
  }

  onTabChange(index: any) {
    switch (Number(index)) {
      case 0:
        this.loadInProcessBids();
        break;
      case 1:
        this.loadPendingBids();
        break;
      case 2:
        this.loadArchivedBids();
        break;
      default:
        console.error("Invalid tab event: ", index);
    }
  }

  openBid(number: any) {
    this.bidRepository.get(Number(number)).subscribe({
      next: (res) => {
        this.viewBid = res;
        this.viewOpened = true;
      }
    });
  }

  onBidClick(number: any) {
    Utils.changeQueryParam(this.route, this.router, { id: number });
  }

  closeView(): void {
    this.viewOpened = false;
    Utils.changeQueryParam(this.route, this.router, { id: null });
  }

  protected readonly Role = Role;
}
