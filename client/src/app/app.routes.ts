import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {AuthorizationComponent} from './components/view/authorization/authorization.component';
import {ForbiddenComponent} from './components/view/forbidden/forbidden.component';
import {NotificationsComponent} from './components/view/notifications/notifications.component';
import {AuthGuard} from './guards/auth.guard';
import {InoutComponent} from './components/view/inout/inout.component';
import {Role} from './models/auth/role.model';
import {BidsComponent} from './components/view/bids/bids.component';
import {PaymentComponent} from './components/view/payment/payment.component';
import {ResidentsComponent} from './components/view/residents/residents.component';
import {StaffComponent} from './components/view/staff/staff.component';

export const routes = [
  { path: 'forbidden', component: ForbiddenComponent },
  { path: 'auth', component: AuthorizationComponent },
  { path: 'notifications', component: NotificationsComponent, canActivate: [AuthGuard] },
  { path: 'inout', component: InoutComponent, canActivate: [AuthGuard], roles: [Role.GUARD, Role.MANAGER, Role.RESIDENT] },
  { path: 'bids', component: BidsComponent, canActivate: [AuthGuard], roles: [Role.NON_RESIDENT, Role.RESIDENT, Role.MANAGER] },
  { path: 'payment', component: PaymentComponent, canActivate: [AuthGuard], roles: [Role.RESIDENT, Role.MANAGER] },
  { path: 'residents', component: ResidentsComponent, canActivate: [AuthGuard], roles: [Role.MANAGER] },
  { path: 'staff', component: StaffComponent, canActivate: [AuthGuard], roles: [Role.MANAGER] },
  { path: '', redirectTo: 'auth', pathMatch: "full" },
  { path: '**', redirectTo: 'auth' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes as Routes)],
  exports: [RouterModule]
})
export class AppRoutes { }
