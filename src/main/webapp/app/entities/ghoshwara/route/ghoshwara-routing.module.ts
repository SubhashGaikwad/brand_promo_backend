import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GhoshwaraComponent } from '../list/ghoshwara.component';
import { GhoshwaraDetailComponent } from '../detail/ghoshwara-detail.component';
import { GhoshwaraUpdateComponent } from '../update/ghoshwara-update.component';
import { GhoshwaraRoutingResolveService } from './ghoshwara-routing-resolve.service';

const ghoshwaraRoute: Routes = [
  {
    path: '',
    component: GhoshwaraComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GhoshwaraDetailComponent,
    resolve: {
      ghoshwara: GhoshwaraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GhoshwaraUpdateComponent,
    resolve: {
      ghoshwara: GhoshwaraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GhoshwaraUpdateComponent,
    resolve: {
      ghoshwara: GhoshwaraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ghoshwaraRoute)],
  exports: [RouterModule],
})
export class GhoshwaraRoutingModule {}
