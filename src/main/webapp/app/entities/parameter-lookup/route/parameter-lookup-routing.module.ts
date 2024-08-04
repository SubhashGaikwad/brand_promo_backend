import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ParameterLookupComponent } from '../list/parameter-lookup.component';
import { ParameterLookupDetailComponent } from '../detail/parameter-lookup-detail.component';
import { ParameterLookupUpdateComponent } from '../update/parameter-lookup-update.component';
import { ParameterLookupRoutingResolveService } from './parameter-lookup-routing-resolve.service';

const parameterLookupRoute: Routes = [
  {
    path: '',
    component: ParameterLookupComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParameterLookupDetailComponent,
    resolve: {
      parameterLookup: ParameterLookupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParameterLookupUpdateComponent,
    resolve: {
      parameterLookup: ParameterLookupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParameterLookupUpdateComponent,
    resolve: {
      parameterLookup: ParameterLookupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(parameterLookupRoute)],
  exports: [RouterModule],
})
export class ParameterLookupRoutingModule {}
