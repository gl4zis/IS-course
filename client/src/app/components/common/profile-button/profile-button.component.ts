import {Component, OnInit} from '@angular/core';
import {Button} from 'primeng/button';
import {IconFieldModule} from 'primeng/iconfield';
import {AvatarModule} from 'primeng/avatar';
import {AuthService} from '../../../services/auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'profile-button',
  standalone: true,
  imports: [
    Button,
    IconFieldModule,
    AvatarModule
  ],
  templateUrl: './profile-button.component.html'
})
export class ProfileButtonComponent implements OnInit {
  login?: string;

  constructor(
    private authService: AuthService,
    protected router: Router,
  ) {}

  ngOnInit() {
    this.authService.isAuthorized$.subscribe(() => this.login = this.authService.getLogin());
  }
}
