import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DakJourneyComponent } from '../list/dak-journey.component';
import { DakJourneyDetailComponent } from '../detail/dak-journey-detail.component';
import { DakJourneyUpdateComponent } from '../update/dak-journey-update.component';
import { DakJourneyRoutingResolveService } from './dak-journey-routing-resolve.service';

const dakJourneyRoute: Routes = [
  {
    path: '',
    component: DakJourneyComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DakJourneyDetailComponent,
    resolve: {
      dakJourney: DakJourneyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DakJourneyUpdateComponent,
    resolve: {
      dakJourney: DakJourneyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DakJourneyUpdateComponent,
    resolve: {
      dakJourney: DakJourneyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dakJourneyRoute)],
  exports: [RouterModule],
})
export class DakJourneyRoutingModule {}
