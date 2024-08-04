import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecurityUser } from '../security-user.model';
import { SecurityUserService } from '../service/security-user.service';

@Component({
  templateUrl: './security-user-delete-dialog.component.html',
})
export class SecurityUserDeleteDialogComponent {
  securityUser?: ISecurityUser;

  constructor(protected securityUserService: SecurityUserService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.securityUserService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
