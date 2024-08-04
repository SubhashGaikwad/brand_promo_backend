import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHearingDetails } from '../hearing-details.model';
import { HearingDetailsService } from '../service/hearing-details.service';

@Component({
  templateUrl: './hearing-details-delete-dialog.component.html',
})
export class HearingDetailsDeleteDialogComponent {
  hearingDetails?: IHearingDetails;

  constructor(protected hearingDetailsService: HearingDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hearingDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
