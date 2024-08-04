import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HearingDetailsComponent } from './list/hearing-details.component';
import { HearingDetailsDetailComponent } from './detail/hearing-details-detail.component';
import { HearingDetailsUpdateComponent } from './update/hearing-details-update.component';
import { HearingDetailsDeleteDialogComponent } from './delete/hearing-details-delete-dialog.component';
import { HearingDetailsRoutingModule } from './route/hearing-details-routing.module';

@NgModule({
  imports: [SharedModule, HearingDetailsRoutingModule],
  declarations: [
    HearingDetailsComponent,
    HearingDetailsDetailComponent,
    HearingDetailsUpdateComponent,
    HearingDetailsDeleteDialogComponent,
  ],
  entryComponents: [HearingDetailsDeleteDialogComponent],
})
export class HearingDetailsModule {}
