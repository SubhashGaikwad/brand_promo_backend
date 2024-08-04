import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SecurityPermissionComponent } from '../list/security-permission.component';
import { SecurityPermissionDetailComponent } from '../detail/security-permission-detail.component';
import { SecurityPermissionUpdateComponent } from '../update/security-permission-update.component';
import { SecurityPermissionRoutingResolveService } from './security-permission-routing-resolve.service';

const securityPermissionRoute: Routes = [
  {
    path: '',
    component: SecurityPermissionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecurityPermissionDetailComponent,
    resolve: {
      securityPermission: SecurityPermissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecurityPermissionUpdateComponent,
    resolve: {
      securityPermission: SecurityPermissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecurityPermissionUpdateComponent,
    resolve: {
      securityPermission: SecurityPermissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(securityPermissionRoute)],
  exports: [RouterModule],
})
export class SecurityPermissionRoutingModule {}
