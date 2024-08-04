import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommentMaster } from '../comment-master.model';
import { CommentMasterService } from '../service/comment-master.service';

@Component({
  templateUrl: './comment-master-delete-dialog.component.html',
})
export class CommentMasterDeleteDialogComponent {
  commentMaster?: ICommentMaster;

  constructor(protected commentMasterService: CommentMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commentMasterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
