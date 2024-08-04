import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganization } from '../organization.model';
import { OrganizationService } from '../service/organization.service';

@Component({
  templateUrl: './organization-delete-dialog.component.html',
})
export class OrganizationDeleteDialogComponent {
  organization?: IOrganization;

  constructor(protected organizationService: OrganizationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organizationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
