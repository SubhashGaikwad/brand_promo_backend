import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserAccessComponent } from '../list/user-access.component';
import { UserAccessDetailComponent } from '../detail/user-access-detail.component';
import { UserAccessUpdateComponent } from '../update/user-access-update.component';
import { UserAccessRoutingResolveService } from './user-access-routing-resolve.service';

const userAccessRoute: Routes = [
  {
    path: '',
    component: UserAccessComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserAccessDetailComponent,
    resolve: {
      userAccess: UserAccessRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserAccessUpdateComponent,
    resolve: {
      userAccess: UserAccessRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserAccessUpdateComponent,
    resolve: {
      userAccess: UserAccessRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userAccessRoute)],
  exports: [RouterModule],
})
export class UserAccessRoutingModule {}
