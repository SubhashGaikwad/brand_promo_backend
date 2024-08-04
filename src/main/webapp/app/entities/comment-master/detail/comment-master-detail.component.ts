import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommentMaster } from '../comment-master.model';

@Component({
  selector: 'jhi-comment-master-detail',
  templateUrl: './comment-master-detail.component.html',
})
export class CommentMasterDetailComponent implements OnInit {
  commentMaster: ICommentMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commentMaster }) => {
      this.commentMaster = commentMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
