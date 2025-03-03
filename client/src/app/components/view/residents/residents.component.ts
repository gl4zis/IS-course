import {Component, OnInit} from '@angular/core';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';
import {Button} from 'primeng/button';
import {NgIf} from '@angular/common';
import {PrimeTemplate} from 'primeng/api';
import {TableModule} from 'primeng/table';
import {Role} from '../../../models/auth/role.model';
import {Resident} from '../../../models/user/resident.model';
import {UserRepository} from '../../../repositories/user.repository';
import {RouterLink} from '@angular/router';
import {Utils} from '../../../services/utils';

@Component({
  selector: 'residents-view',
  standalone: true,
  templateUrl: './residents.component.html',
  imports: [
    NavHeaderComponent,
    Button,
    NgIf,
    PrimeTemplate,
    TableModule,
    RouterLink
  ]
})
export class ResidentsComponent implements OnInit {
  residents: Resident[] = [];

  constructor(
    protected userRepository: UserRepository
  ) {}

  ngOnInit(): void {
    this.loadResidents();
  }

  loadResidents(): void {
    this.userRepository.getResidents().subscribe({
      next: (resp) => {
        this.residents = resp.map(r => ({...r, lastCameOut: r.lastCameOut && new Date(r.lastCameOut)}));
      }
    });
  }

  protected readonly Role = Role;
  protected readonly Utils = Utils;
}
