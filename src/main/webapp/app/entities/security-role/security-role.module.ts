import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SecurityRoleComponent } from './list/security-role.component';
import { SecurityRoleDetailComponent } from './detail/security-role-detail.component';
import { SecurityRoleUpdateComponent } from './update/security-role-update.component';
import { SecurityRoleDeleteDialogComponent } from './delete/security-role-delete-dialog.component';
import { SecurityRoleRoutingModule } from './route/security-role-routing.module';

@NgModule({
  imports: [SharedModule, SecurityRoleRoutingModule],
  declarations: [SecurityRoleComponent, SecurityRoleDetailComponent, SecurityRoleUpdateComponent, SecurityRoleDeleteDialogComponent],
  entryComponents: [SecurityRoleDeleteDialogComponent],
})
export class SecurityRoleModule {}
