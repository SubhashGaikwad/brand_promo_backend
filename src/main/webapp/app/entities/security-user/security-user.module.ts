import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SecurityUserComponent } from './list/security-user.component';
import { SecurityUserDetailComponent } from './detail/security-user-detail.component';
import { SecurityUserUpdateComponent } from './update/security-user-update.component';
import { SecurityUserDeleteDialogComponent } from './delete/security-user-delete-dialog.component';
import { SecurityUserRoutingModule } from './route/security-user-routing.module';

@NgModule({
  imports: [SharedModule, SecurityUserRoutingModule],
  declarations: [SecurityUserComponent, SecurityUserDetailComponent, SecurityUserUpdateComponent, SecurityUserDeleteDialogComponent],
  entryComponents: [SecurityUserDeleteDialogComponent],
})
export class SecurityUserModule {}
