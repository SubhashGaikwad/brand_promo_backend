import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SecurityPermissionComponent } from './list/security-permission.component';
import { SecurityPermissionDetailComponent } from './detail/security-permission-detail.component';
import { SecurityPermissionUpdateComponent } from './update/security-permission-update.component';
import { SecurityPermissionDeleteDialogComponent } from './delete/security-permission-delete-dialog.component';
import { SecurityPermissionRoutingModule } from './route/security-permission-routing.module';

@NgModule({
  imports: [SharedModule, SecurityPermissionRoutingModule],
  declarations: [
    SecurityPermissionComponent,
    SecurityPermissionDetailComponent,
    SecurityPermissionUpdateComponent,
    SecurityPermissionDeleteDialogComponent,
  ],
  entryComponents: [SecurityPermissionDeleteDialogComponent],
})
export class SecurityPermissionModule {}
