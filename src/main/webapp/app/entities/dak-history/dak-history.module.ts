import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DakHistoryComponent } from './list/dak-history.component';
import { DakHistoryDetailComponent } from './detail/dak-history-detail.component';
import { DakHistoryUpdateComponent } from './update/dak-history-update.component';
import { DakHistoryDeleteDialogComponent } from './delete/dak-history-delete-dialog.component';
import { DakHistoryRoutingModule } from './route/dak-history-routing.module';

@NgModule({
  imports: [SharedModule, DakHistoryRoutingModule],
  declarations: [DakHistoryComponent, DakHistoryDetailComponent, DakHistoryUpdateComponent, DakHistoryDeleteDialogComponent],
  entryComponents: [DakHistoryDeleteDialogComponent],
})
export class DakHistoryModule {}
