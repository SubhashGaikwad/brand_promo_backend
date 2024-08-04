import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DakMasterComponent } from './list/dak-master.component';
import { DakMasterDetailComponent } from './detail/dak-master-detail.component';
import { DakMasterUpdateComponent } from './update/dak-master-update.component';
import { DakMasterDeleteDialogComponent } from './delete/dak-master-delete-dialog.component';
import { DakMasterRoutingModule } from './route/dak-master-routing.module';

@NgModule({
  imports: [SharedModule, DakMasterRoutingModule],
  declarations: [DakMasterComponent, DakMasterDetailComponent, DakMasterUpdateComponent, DakMasterDeleteDialogComponent],
  entryComponents: [DakMasterDeleteDialogComponent],
})
export class DakMasterModule {}
