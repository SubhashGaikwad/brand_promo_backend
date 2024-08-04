import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganizationComponent } from '../list/organization.component';
import { OrganizationDetailComponent } from '../detail/organization-detail.component';
import { OrganizationUpdateComponent } from '../update/organization-update.component';
import { OrganizationRoutingResolveService } from './organization-routing-resolve.service';

const organizationRoute: Routes = [
  {
    path: '',
    component: OrganizationComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganizationDetailComponent,
    resolve: {
      organization: OrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganizationUpdateComponent,
    resolve: {
      organization: OrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganizationUpdateComponent,
    resolve: {
      organization: OrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organizationRoute)],
  exports: [RouterModule],
})
export class OrganizationRoutingModule {}
