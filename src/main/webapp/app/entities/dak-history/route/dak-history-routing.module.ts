import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DakHistoryComponent } from '../list/dak-history.component';
import { DakHistoryDetailComponent } from '../detail/dak-history-detail.component';
import { DakHistoryUpdateComponent } from '../update/dak-history-update.component';
import { DakHistoryRoutingResolveService } from './dak-history-routing-resolve.service';

const dakHistoryRoute: Routes = [
  {
    path: '',
    component: DakHistoryComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DakHistoryDetailComponent,
    resolve: {
      dakHistory: DakHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DakHistoryUpdateComponent,
    resolve: {
      dakHistory: DakHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DakHistoryUpdateComponent,
    resolve: {
      dakHistory: DakHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dakHistoryRoute)],
  exports: [RouterModule],
})
export class DakHistoryRoutingModule {}
