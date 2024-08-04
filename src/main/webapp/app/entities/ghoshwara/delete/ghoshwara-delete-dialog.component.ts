import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGhoshwara } from '../ghoshwara.model';
import { GhoshwaraService } from '../service/ghoshwara.service';

@Component({
  templateUrl: './ghoshwara-delete-dialog.component.html',
})
export class GhoshwaraDeleteDialogComponent {
  ghoshwara?: IGhoshwara;

  constructor(protected ghoshwaraService: GhoshwaraService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ghoshwaraService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
