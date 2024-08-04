import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDakMaster } from '../dak-master.model';
import { DakMasterService } from '../service/dak-master.service';

@Component({
  templateUrl: './dak-master-delete-dialog.component.html',
})
export class DakMasterDeleteDialogComponent {
  dakMaster?: IDakMaster;

  constructor(protected dakMasterService: DakMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dakMasterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
