import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CommentMasterComponent } from '../list/comment-master.component';
import { CommentMasterDetailComponent } from '../detail/comment-master-detail.component';
import { CommentMasterUpdateComponent } from '../update/comment-master-update.component';
import { CommentMasterRoutingResolveService } from './comment-master-routing-resolve.service';

const commentMasterRoute: Routes = [
  {
    path: '',
    component: CommentMasterComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommentMasterDetailComponent,
    resolve: {
      commentMaster: CommentMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommentMasterUpdateComponent,
    resolve: {
      commentMaster: CommentMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommentMasterUpdateComponent,
    resolve: {
      commentMaster: CommentMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(commentMasterRoute)],
  exports: [RouterModule],
})
export class CommentMasterRoutingModule {}
