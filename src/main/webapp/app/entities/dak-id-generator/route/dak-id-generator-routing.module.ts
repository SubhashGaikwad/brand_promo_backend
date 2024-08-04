import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DakIdGeneratorComponent } from '../list/dak-id-generator.component';
import { DakIdGeneratorDetailComponent } from '../detail/dak-id-generator-detail.component';
import { DakIdGeneratorUpdateComponent } from '../update/dak-id-generator-update.component';
import { DakIdGeneratorRoutingResolveService } from './dak-id-generator-routing-resolve.service';

const dakIdGeneratorRoute: Routes = [
  {
    path: '',
    component: DakIdGeneratorComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DakIdGeneratorDetailComponent,
    resolve: {
      dakIdGenerator: DakIdGeneratorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DakIdGeneratorUpdateComponent,
    resolve: {
      dakIdGenerator: DakIdGeneratorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DakIdGeneratorUpdateComponent,
    resolve: {
      dakIdGenerator: DakIdGeneratorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dakIdGeneratorRoute)],
  exports: [RouterModule],
})
export class DakIdGeneratorRoutingModule {}
