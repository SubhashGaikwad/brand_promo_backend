import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ParameterLookupComponent } from './list/parameter-lookup.component';
import { ParameterLookupDetailComponent } from './detail/parameter-lookup-detail.component';
import { ParameterLookupUpdateComponent } from './update/parameter-lookup-update.component';
import { ParameterLookupDeleteDialogComponent } from './delete/parameter-lookup-delete-dialog.component';
import { ParameterLookupRoutingModule } from './route/parameter-lookup-routing.module';

@NgModule({
  imports: [SharedModule, ParameterLookupRoutingModule],
  declarations: [
    ParameterLookupComponent,
    ParameterLookupDetailComponent,
    ParameterLookupUpdateComponent,
    ParameterLookupDeleteDialogComponent,
  ],
  entryComponents: [ParameterLookupDeleteDialogComponent],
})
export class ParameterLookupModule {}
