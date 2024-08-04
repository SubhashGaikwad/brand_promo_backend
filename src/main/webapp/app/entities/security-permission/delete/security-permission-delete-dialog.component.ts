import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecurityPermission } from '../security-permission.model';
import { SecurityPermissionService } from '../service/security-permission.service';

@Component({
  templateUrl: './security-permission-delete-dialog.component.html',
})
export class SecurityPermissionDeleteDialogComponent {
  securityPermission?: ISecurityPermission;

  constructor(protected securityPermissionService: SecurityPermissionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.securityPermissionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
