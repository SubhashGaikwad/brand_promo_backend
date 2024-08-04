import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserAccess } from '../user-access.model';
import { UserAccessService } from '../service/user-access.service';

@Component({
  templateUrl: './user-access-delete-dialog.component.html',
})
export class UserAccessDeleteDialogComponent {
  userAccess?: IUserAccess;

  constructor(protected userAccessService: UserAccessService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userAccessService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
