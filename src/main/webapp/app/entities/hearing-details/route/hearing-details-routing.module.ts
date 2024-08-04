import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HearingDetailsComponent } from '../list/hearing-details.component';
import { HearingDetailsDetailComponent } from '../detail/hearing-details-detail.component';
import { HearingDetailsUpdateComponent } from '../update/hearing-details-update.component';
import { HearingDetailsRoutingResolveService } from './hearing-details-routing-resolve.service';

const hearingDetailsRoute: Routes = [
  {
    path: '',
    component: HearingDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HearingDetailsDetailComponent,
    resolve: {
      hearingDetails: HearingDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HearingDetailsUpdateComponent,
    resolve: {
      hearingDetails: HearingDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HearingDetailsUpdateComponent,
    resolve: {
      hearingDetails: HearingDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hearingDetailsRoute)],
  exports: [RouterModule],
})
export class HearingDetailsRoutingModule {}
