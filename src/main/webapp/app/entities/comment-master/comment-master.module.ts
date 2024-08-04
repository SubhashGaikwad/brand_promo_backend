import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommentMasterComponent } from './list/comment-master.component';
import { CommentMasterDetailComponent } from './detail/comment-master-detail.component';
import { CommentMasterUpdateComponent } from './update/comment-master-update.component';
import { CommentMasterDeleteDialogComponent } from './delete/comment-master-delete-dialog.component';
import { CommentMasterRoutingModule } from './route/comment-master-routing.module';

@NgModule({
  imports: [SharedModule, CommentMasterRoutingModule],
  declarations: [CommentMasterComponent, CommentMasterDetailComponent, CommentMasterUpdateComponent, CommentMasterDeleteDialogComponent],
  entryComponents: [CommentMasterDeleteDialogComponent],
})
export class CommentMasterModule {}
