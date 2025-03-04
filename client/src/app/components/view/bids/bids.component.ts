import {Component, OnInit} from '@angular/core';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';
import {AuthService} from '../../../services/auth.service';
import {
  Bid,
  BID_STATUS_COLOR_MAP,
  BID_TYPE_MAP,
  BidType, DepartureBid,
  DepartureData, isEditableBidStatus,
  OccupationBid, RoomChangeBid
} from '../../../models/bid/bid.model';
import {BidRepository} from '../../../repositories/bid.repository';
import {Role} from '../../../models/auth/role.model';
import {Tab, TabList, TabPanel, TabPanels, Tabs} from 'primeng/tabs';
import {NgForOf, NgIf} from '@angular/common';
import {BidCardComponent} from './bid-card/bid-card.component';
import {ActivatedRoute, Router} from '@angular/router';
import {Utils} from '../../../services/utils';
import {Dialog} from 'primeng/dialog';
import {Button} from 'primeng/button';
import {InputText} from 'primeng/inputtext';
import {Textarea} from 'primeng/textarea';
import {PrimeTemplate} from 'primeng/api';
import {University} from '../../../models/university/university.model';
import {Dormitory} from '../../../models/dormitory/dormitory.model';
import {localizeRoomType, Room, RoomType} from '../../../models/room/room.model';
import {UniversityRepository} from '../../../repositories/university.repository';
import {DormitoryRepository} from '../../../repositories/dormitory.repository';
import {RoomRepository} from '../../../repositories/room.repository';
import {FormsModule} from '@angular/forms';
import {FileRepository} from '../../../repositories/file.repository';

interface OccupationDataView {
  university: University;
  dormitory: Dormitory;
}

interface DepartureDataView extends DepartureData {}

interface RoomChangeDataView {
  roomTo?: Room;
  roomPreferType?: RoomType;
}

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
    Button,
    InputText,
    Textarea,
    PrimeTemplate,
    FormsModule
  ]
})
export class BidsComponent implements OnInit {
  selfBids: Bid[] = [];

  currTabIndex = 0;
  inProcessBids: Bid[] = [];
  pendingBids: Bid[] = [];
  archivedBids: Bid[] = [];

  viewOpened = false;
  viewBid?: Bid;
  occupationData?: OccupationDataView;
  departureData?: DepartureDataView;
  roomChangeData?: RoomChangeDataView;
  universityOptions: University[] = [];
  dormitoryOptions: Dormitory[] = [];
  roomOptions: Room[] = [];

  comment = '';
  commentDialogOpened = false;
  isCommentForDeny = false;

  constructor(
    protected authService: AuthService,
    protected fileRepository: FileRepository,
    private bidRepository: BidRepository,
    private universityRepository: UniversityRepository,
    private dormitoryRepository: DormitoryRepository,
    private roomRepository: RoomRepository,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['id']) {
        this.openBid(Number(params['id']), this.authService.getRole() !== Role.MANAGER);
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
    this.currTabIndex = Number(index);
  }

  openBid(number: any, toEdit: boolean) {
    this.bidRepository.get(Number(number)).subscribe({
      next: (bid) => {
        this.viewBid = bid;

        if (this.viewBid.type === BidType.OCCUPATION) {
          const occBid = (this.viewBid as OccupationBid);
          this.occupationData = {
            university: {} as University,
            dormitory: {} as Dormitory,
          };

          this.universityRepository.get(occBid.universityId).subscribe({
            next: (university) => this.occupationData!.university = university,
          });
          this.dormitoryRepository.get(occBid.dormitoryId).subscribe({
            next: (dormitory) => this.occupationData!.dormitory = dormitory,
          });

          if (toEdit && isEditableBidStatus(this.viewBid.status)) {
            this.loadDormOptions(occBid.universityId);
          }
        } else if (this.viewBid.type === BidType.DEPARTURE) {
          const depBid = (this.viewBid as DepartureBid);
          this.departureData = {
            dayFrom: new Date(depBid.dayFrom),
            dayTo: new Date(depBid.dayTo)
          };
        } else if (this.viewBid.type === BidType.ROOM_CHANGE) {
          const rcBid = (this.viewBid as RoomChangeBid);
          this.roomChangeData = {
            roomTo: undefined,
            roomPreferType: rcBid.roomPreferType
          };

          if (rcBid.roomToId) {
            this.roomRepository.get(rcBid.roomToId).subscribe({
              next: (room) => this.roomChangeData!.roomTo = room
            });
          }

          if (toEdit && isEditableBidStatus(this.viewBid.status)) {
            this.loadRoomOptions();
          }
        }

        this.viewOpened = true;
      }
    });

    this.loadUniversityOptions();
  }

  newBid() {
    this.viewOpened = true;
    this.loadUniversityOptions();
  }

  loadUniversityOptions() {
    this.universityRepository.getAll().subscribe({
      next: (res) => this.universityOptions = res
    });
  }

  loadDormOptions(universityId: number) {
    this.dormitoryRepository.getAll().subscribe({
      next: (res) => this.dormitoryOptions = res.filter(d => d.universityIds.includes(universityId))
    });
  }

  loadRoomOptions() {
    const resident = this.authService.getRole() === Role.RESIDENT ?
      this.authService.getLogin() : this.viewBid?.sender.login;

    this.roomRepository.getAvailableForResident(resident!).subscribe({
      next: (res) => this.roomOptions = res
    });
  }

  onBidClick(number: any) {
    Utils.changeQueryParam(this.route, this.router, { id: number });
  }

  closeView(): void {
    this.viewOpened = false;
    this.resetForms();
    Utils.changeQueryParam(this.route, this.router, { id: null });
  }

  resetForms() {
    this.viewBid = undefined;
    this.occupationData = undefined;
    this.roomChangeData = undefined;
    this.departureData = undefined;
    this.comment = '';
    this.isCommentForDeny = false;
  }

  redirectToSender(): void {
    if (!this.viewBid || this.viewBid.sender.role !== Role.RESIDENT) {
      return;
    }
    this.router.navigate(['residents'], { queryParams: { search: this.viewBid.sender.login } })
  }

  denyBid() {
    this.commentDialogOpened = true;
    this.isCommentForDeny = true;
  }

  acceptBid() {
    this.bidRepository.accept(this.viewBid!.number).subscribe({
      next: () => {
        this.closeView();
        this.onTabChange(this.currTabIndex);
      }
    });
  }

  pendBid() {
    this.commentDialogOpened = true;
    this.isCommentForDeny = false;
  }

  saveComment() {
    if (this.isCommentForDeny) {
      this.bidRepository.deny(this.viewBid!.number, this.comment).subscribe({
        next: () => {
          this.commentDialogOpened = false;
          this.closeView();
          this.onTabChange(this.currTabIndex);
        }
      });
    } else {
      this.bidRepository.pend(this.viewBid!.number, this.comment).subscribe({
        next: () => {
          this.commentDialogOpened = false;
          this.closeView();
          this.onTabChange(this.currTabIndex);
        }
      });
    }
  }

  protected readonly Role = Role;
  protected readonly BID_TYPE_MAP = BID_TYPE_MAP;
  protected readonly BID_STATUS_COLOR_MAP = BID_STATUS_COLOR_MAP;
  protected readonly Utils = Utils;
  protected readonly localizeRoomType = localizeRoomType;
}
