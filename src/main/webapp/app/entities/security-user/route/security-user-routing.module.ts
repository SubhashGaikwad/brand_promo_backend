import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SecurityUserComponent } from '../list/security-user.component';
import { SecurityUserDetailComponent } from '../detail/security-user-detail.component';
import { SecurityUserUpdateComponent } from '../update/security-user-update.component';
import { SecurityUserRoutingResolveService } from './security-user-routing-resolve.service';

const securityUserRoute: Routes = [
  {
    path: '',
    component: SecurityUserComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecurityUserDetailComponent,
    resolve: {
      securityUser: SecurityUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecurityUserUpdateComponent,
    resolve: {
      securityUser: SecurityUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecurityUserUpdateComponent,
    resolve: {
      securityUser: SecurityUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(securityUserRoute)],
  exports: [RouterModule],
})
export class SecurityUserRoutingModule {}
