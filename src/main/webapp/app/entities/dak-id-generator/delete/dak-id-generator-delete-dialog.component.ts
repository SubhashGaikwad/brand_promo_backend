import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDakIdGenerator } from '../dak-id-generator.model';
import { DakIdGeneratorService } from '../service/dak-id-generator.service';

@Component({
  templateUrl: './dak-id-generator-delete-dialog.component.html',
})
export class DakIdGeneratorDeleteDialogComponent {
  dakIdGenerator?: IDakIdGenerator;

  constructor(protected dakIdGeneratorService: DakIdGeneratorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dakIdGeneratorService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
