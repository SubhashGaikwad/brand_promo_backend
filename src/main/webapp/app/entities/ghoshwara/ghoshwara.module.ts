import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GhoshwaraComponent } from './list/ghoshwara.component';
import { GhoshwaraDetailComponent } from './detail/ghoshwara-detail.component';
import { GhoshwaraUpdateComponent } from './update/ghoshwara-update.component';
import { GhoshwaraDeleteDialogComponent } from './delete/ghoshwara-delete-dialog.component';
import { GhoshwaraRoutingModule } from './route/ghoshwara-routing.module';

@NgModule({
  imports: [SharedModule, GhoshwaraRoutingModule],
  declarations: [GhoshwaraComponent, GhoshwaraDetailComponent, GhoshwaraUpdateComponent, GhoshwaraDeleteDialogComponent],
  entryComponents: [GhoshwaraDeleteDialogComponent],
})
export class GhoshwaraModule {}
