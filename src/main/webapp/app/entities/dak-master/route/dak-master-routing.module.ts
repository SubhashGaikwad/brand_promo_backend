import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DakMasterComponent } from '../list/dak-master.component';
import { DakMasterDetailComponent } from '../detail/dak-master-detail.component';
import { DakMasterUpdateComponent } from '../update/dak-master-update.component';
import { DakMasterRoutingResolveService } from './dak-master-routing-resolve.service';

const dakMasterRoute: Routes = [
  {
    path: '',
    component: DakMasterComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DakMasterDetailComponent,
    resolve: {
      dakMaster: DakMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DakMasterUpdateComponent,
    resolve: {
      dakMaster: DakMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DakMasterUpdateComponent,
    resolve: {
      dakMaster: DakMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dakMasterRoute)],
  exports: [RouterModule],
})
export class DakMasterRoutingModule {}
