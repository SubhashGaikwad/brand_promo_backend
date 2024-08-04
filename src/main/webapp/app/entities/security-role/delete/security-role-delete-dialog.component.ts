import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecurityRole } from '../security-role.model';
import { SecurityRoleService } from '../service/security-role.service';

@Component({
  templateUrl: './security-role-delete-dialog.component.html',
})
export class SecurityRoleDeleteDialogComponent {
  securityRole?: ISecurityRole;

  constructor(protected securityRoleService: SecurityRoleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.securityRoleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
