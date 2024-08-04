import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DakJourneyComponent } from './list/dak-journey.component';
import { DakJourneyDetailComponent } from './detail/dak-journey-detail.component';
import { DakJourneyUpdateComponent } from './update/dak-journey-update.component';
import { DakJourneyDeleteDialogComponent } from './delete/dak-journey-delete-dialog.component';
import { DakJourneyRoutingModule } from './route/dak-journey-routing.module';

@NgModule({
  imports: [SharedModule, DakJourneyRoutingModule],
  declarations: [DakJourneyComponent, DakJourneyDetailComponent, DakJourneyUpdateComponent, DakJourneyDeleteDialogComponent],
  entryComponents: [DakJourneyDeleteDialogComponent],
})
export class DakJourneyModule {}
