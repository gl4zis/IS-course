import {Component, OnDestroy, OnInit} from '@angular/core';
import {GuardRepository} from '../../../repositories/guard.repository';
import {ToastService} from '../../../services/toast.service';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';
import {Card} from 'primeng/card';
import {PrimeTemplate} from 'primeng/api';
import {FloatLabel} from 'primeng/floatlabel';
import {FormsModule} from '@angular/forms';
import {InputText} from 'primeng/inputtext';
import {Button} from 'primeng/button';
import {AuthService} from '../../../services/auth.service';
import {NgIf} from '@angular/common';
import {Role} from '../../../models/auth/role.model';
import {TableModule} from 'primeng/table';
import {GuardHistory} from '../../../models/guard/guard-history.model';
import {ActivatedRoute, Router} from '@angular/router';
import { Utils } from '../../../services/utils';

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
    Button,
    NgIf,
    TableModule
  ],
  templateUrl: './inout.component.html'
})
export class InoutComponent implements OnInit {
  login: string = "";

  residentForHistory: string = "";
  history: GuardHistory[] = [];

  constructor(
    protected authService: AuthService,
    private guardRepository: GuardRepository,
    private toast: ToastService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    if (this.authService.getRole() === Role.MANAGER) {
      this.route.queryParams.subscribe(params => {
        if (params['searchLogin']) {
          this.residentForHistory = params['searchLogin'];
          this.loadHistory();
        } else {
          this.residentForHistory = "";
        }
      });
    } else {
      this.loadSelfHistory();
    }
  }

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

  findResidentHistory(): void {
    if (this.residentForHistory && this.residentForHistory.length) {
      Utils.changeQueryParam(this.route, this.router, { searchLogin: this.residentForHistory });
    }
  }

  private loadHistory() {
    this.guardRepository.getHistory(this.residentForHistory).subscribe({
      next: (resp) => this.history = resp
    });
  }

  private loadSelfHistory() {
    this.guardRepository.getSelfHistory().subscribe({
      next: (resp) => this.history = resp
    });
  }

  protected readonly Role = Role;
}
