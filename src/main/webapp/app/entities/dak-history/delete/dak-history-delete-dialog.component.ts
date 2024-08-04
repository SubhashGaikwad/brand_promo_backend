import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDakHistory } from '../dak-history.model';
import { DakHistoryService } from '../service/dak-history.service';

@Component({
  templateUrl: './dak-history-delete-dialog.component.html',
})
export class DakHistoryDeleteDialogComponent {
  dakHistory?: IDakHistory;

  constructor(protected dakHistoryService: DakHistoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dakHistoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
