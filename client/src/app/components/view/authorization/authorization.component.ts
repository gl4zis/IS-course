import {Component} from '@angular/core';
import {NavHeaderComponent} from '../../common/nav-header/nav-header.component';
import {Card} from 'primeng/card';
import {LoginReq} from '../../../models/auth/login.model';
import { RegisterReq } from '../../../models/auth/register.model';
import {PrimeTemplate} from 'primeng/api';
import {NgIf} from '@angular/common';
import {Button} from 'primeng/button';
import {InputText} from 'primeng/inputtext';
import {FormsModule} from '@angular/forms';
import {Password} from 'primeng/password';
import {FloatLabelModule} from 'primeng/floatlabel';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'authorization-view',
  standalone: true,
  imports: [
    NavHeaderComponent,
    Card,
    PrimeTemplate,
    NgIf,
    Button,
    InputText,
    FormsModule,
    FloatLabelModule,
    Password
  ],
  templateUrl: './authorization.component.html'
})
export class AuthorizationComponent {
  isSignIn = true;
  form: LoginReq | RegisterReq = {
    login: '',
    password: ''
  };

  constructor(
    protected authService: AuthService,
  ) {}

  onSubmit(): void {
    if (this.isSignIn) {
      this.authService.login(this.form!);
    } else {
      this.authService.register(this.form as RegisterReq);
    }
    this.resetForm();
  }

  changeForm(): void {
    this.isSignIn = !this.isSignIn;
    this.resetForm();
  }

  getTitle(): string {
    if (this.authService.isAuthorized()) {
      return 'Profile';
    }
    return this.isSignIn ? 'Sign In' : 'Sign Up';
  }

  private resetForm(): void {
    this.form = {
      login: '',
      password: ''
    }
  }
}
