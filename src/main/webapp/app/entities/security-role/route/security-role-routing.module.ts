import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SecurityRoleComponent } from '../list/security-role.component';
import { SecurityRoleDetailComponent } from '../detail/security-role-detail.component';
import { SecurityRoleUpdateComponent } from '../update/security-role-update.component';
import { SecurityRoleRoutingResolveService } from './security-role-routing-resolve.service';

const securityRoleRoute: Routes = [
  {
    path: '',
    component: SecurityRoleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecurityRoleDetailComponent,
    resolve: {
      securityRole: SecurityRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecurityRoleUpdateComponent,
    resolve: {
      securityRole: SecurityRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecurityRoleUpdateComponent,
    resolve: {
      securityRole: SecurityRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(securityRoleRoute)],
  exports: [RouterModule],
})
export class SecurityRoleRoutingModule {}
