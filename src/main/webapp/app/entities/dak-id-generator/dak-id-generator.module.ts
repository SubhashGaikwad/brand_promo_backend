import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DakIdGeneratorComponent } from './list/dak-id-generator.component';
import { DakIdGeneratorDetailComponent } from './detail/dak-id-generator-detail.component';
import { DakIdGeneratorUpdateComponent } from './update/dak-id-generator-update.component';
import { DakIdGeneratorDeleteDialogComponent } from './delete/dak-id-generator-delete-dialog.component';
import { DakIdGeneratorRoutingModule } from './route/dak-id-generator-routing.module';

@NgModule({
  imports: [SharedModule, DakIdGeneratorRoutingModule],
  declarations: [
    DakIdGeneratorComponent,
    DakIdGeneratorDetailComponent,
    DakIdGeneratorUpdateComponent,
    DakIdGeneratorDeleteDialogComponent,
  ],
  entryComponents: [DakIdGeneratorDeleteDialogComponent],
})
export class DakIdGeneratorModule {}
