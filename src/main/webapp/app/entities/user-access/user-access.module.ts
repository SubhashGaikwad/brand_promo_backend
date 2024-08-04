import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserAccessComponent } from './list/user-access.component';
import { UserAccessDetailComponent } from './detail/user-access-detail.component';
import { UserAccessUpdateComponent } from './update/user-access-update.component';
import { UserAccessDeleteDialogComponent } from './delete/user-access-delete-dialog.component';
import { UserAccessRoutingModule } from './route/user-access-routing.module';

@NgModule({
  imports: [SharedModule, UserAccessRoutingModule],
  declarations: [UserAccessComponent, UserAccessDetailComponent, UserAccessUpdateComponent, UserAccessDeleteDialogComponent],
  entryComponents: [UserAccessDeleteDialogComponent],
})
export class UserAccessModule {}
