import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {AuthorizationComponent} from './components/view/authorization/authorization.component';
import {ForbiddenComponent} from './components/view/forbidden/forbidden.component';
import {NotificationsComponent} from './components/view/notifications/notifications.component';
import {AuthGuard} from './guards/auth.guard';
import {InoutComponent} from './components/view/inout/inout.component';
import {Role} from './models/auth/role.model';
import {BidsComponent} from './components/view/bids/bids.component';

export const routes = [
  { path: 'forbidden', component: ForbiddenComponent },
  { path: 'auth', component: AuthorizationComponent },
  { path: 'notifications', component: NotificationsComponent, canActivate: [AuthGuard] },
  { path: 'inout', component: InoutComponent, canActivate: [AuthGuard], roles: [Role.GUARD, Role.MANAGER, Role.RESIDENT] },
  { path: 'bids', component: BidsComponent, canActivate: [AuthGuard], roles: [Role.NON_RESIDENT, Role.RESIDENT, Role.MANAGER] },
  { path: '', redirectTo: 'auth', pathMatch: "full" },
  { path: '**', redirectTo: 'auth' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes as Routes)],
  exports: [RouterModule]
})
export class AppRoutes { }
