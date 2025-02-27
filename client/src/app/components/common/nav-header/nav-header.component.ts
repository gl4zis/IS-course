import {Component, OnDestroy, OnInit} from '@angular/core';
import {MenubarModule} from 'primeng/menubar';
import {MenuItem} from 'primeng/api';
import {ProfileButtonComponent} from '../profile-button/profile-button.component';
import {Role} from '../../../models/auth/role.model';
import {AuthService} from '../../../services/auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'nav-header',
  standalone: true,
  imports: [
    MenubarModule,
    ProfileButtonComponent
  ],
  templateUrl: './nav-header.component.html'
})
export class NavHeaderComponent implements OnInit {
  private readonly basePages: MenuItem[] = [
    {
      label: 'Notifications',
      command: () => this.router.navigate(['notifications']),
      icon: 'pi pi-envelope'
    }
  ];

  private readonly nonResidentPages: MenuItem[] = [
    {
      label: 'Occupation',
      command: () => this.router.navigate(['occupation']),
      icon: 'pi pi-home'
    }
  ];

  private readonly residentPages: MenuItem[] = [
    {
      label: 'Bids',
      command: () => this.router.navigate(['bids']),
      icon: 'pi pi-file-edit'
    },
    {
      label: 'Payment',
      command: () => this.router.navigate(['payment']),
      icon: 'pi pi-check-circle'
    }
  ];

  private readonly guardPages: MenuItem[] = [
    {
      label: 'In/Out',
      command: () => this.router.navigate(['inout']),
      icon: 'pi pi-arrow-right-arrow-left'
    }
  ];

  private readonly managerPages: MenuItem[] = [
    {
      label: 'Occupation',
      command: () => this.router.navigate(['occupation']),
      icon: 'pi pi-home',

    },
    {
      label: 'Eviction',
      command: () => this.router.navigate(['eviction']),
      icon: 'pi pi-exclamation-circle'
    }
  ];

  pages?: MenuItem[];

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.updateTabs();
    this.authService.isAuthorized$.subscribe(() => this.updateTabs());
  }

  private updateTabs() {
    this.pages = this.basePages;
    const userRole = this.authService.getRole();
    switch (userRole) {
      case Role.NON_RESIDENT:
        this.pages.concat(this.nonResidentPages);
        break;
      case Role.RESIDENT:
        this.pages.concat(this.residentPages);
        break;
      case Role.GUARD:
        this.pages.concat(this.guardPages);
        break;
      case Role.MANAGER:
        this.pages.concat(this.managerPages);
        break;
      default:
        this.pages = [];
    }
  }
}
