import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDakJourney } from '../dak-journey.model';
import { DakJourneyService } from '../service/dak-journey.service';

@Component({
  templateUrl: './dak-journey-delete-dialog.component.html',
})
export class DakJourneyDeleteDialogComponent {
  dakJourney?: IDakJourney;

  constructor(protected dakJourneyService: DakJourneyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dakJourneyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
