import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IParameterLookup } from '../parameter-lookup.model';
import { ParameterLookupService } from '../service/parameter-lookup.service';

@Component({
  templateUrl: './parameter-lookup-delete-dialog.component.html',
})
export class ParameterLookupDeleteDialogComponent {
  parameterLookup?: IParameterLookup;

  constructor(protected parameterLookupService: ParameterLookupService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parameterLookupService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
