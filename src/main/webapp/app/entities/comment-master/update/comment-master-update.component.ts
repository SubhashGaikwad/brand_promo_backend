import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICommentMaster, CommentMaster } from '../comment-master.model';
import { CommentMasterService } from '../service/comment-master.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { DakMasterService } from 'app/entities/dak-master/service/dak-master.service';

@Component({
  selector: 'jhi-comment-master-update',
  templateUrl: './comment-master-update.component.html',
})
export class CommentMasterUpdateComponent implements OnInit {
  isSaving = false;

  securityUsersSharedCollection: ISecurityUser[] = [];
  dakMastersSharedCollection: IDakMaster[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    createdOn: [],
    createdBy: [],
    status: [],
    lastModified: [],
    lastModifiedBy: [],
    securityUser: [],
    dakMaster: [],
  });

  constructor(
    protected commentMasterService: CommentMasterService,
    protected securityUserService: SecurityUserService,
    protected dakMasterService: DakMasterService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commentMaster }) => {
      if (commentMaster.id === undefined) {
        const today = dayjs().startOf('day');
        commentMaster.createdOn = today;
        commentMaster.lastModified = today;
      }

      this.updateForm(commentMaster);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commentMaster = this.createFromForm();
    if (commentMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.commentMasterService.update(commentMaster));
    } else {
      this.subscribeToSaveResponse(this.commentMasterService.create(commentMaster));
    }
  }

  trackSecurityUserById(_index: number, item: ISecurityUser): number {
    return item.id!;
  }

  trackDakMasterById(_index: number, item: IDakMaster): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommentMaster>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(commentMaster: ICommentMaster): void {
    this.editForm.patchValue({
      id: commentMaster.id,
      description: commentMaster.description,
      createdOn: commentMaster.createdOn ? commentMaster.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: commentMaster.createdBy,
      status: commentMaster.status,
      lastModified: commentMaster.lastModified ? commentMaster.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: commentMaster.lastModifiedBy,
      securityUser: commentMaster.securityUser,
      dakMaster: commentMaster.dakMaster,
    });

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      commentMaster.securityUser
    );
    this.dakMastersSharedCollection = this.dakMasterService.addDakMasterToCollectionIfMissing(
      this.dakMastersSharedCollection,
      commentMaster.dakMaster
    );
  }

  protected loadRelationshipsOptions(): void {
    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, this.editForm.get('securityUser')!.value)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));

    this.dakMasterService
      .query()
      .pipe(map((res: HttpResponse<IDakMaster[]>) => res.body ?? []))
      .pipe(
        map((dakMasters: IDakMaster[]) =>
          this.dakMasterService.addDakMasterToCollectionIfMissing(dakMasters, this.editForm.get('dakMaster')!.value)
        )
      )
      .subscribe((dakMasters: IDakMaster[]) => (this.dakMastersSharedCollection = dakMasters));
  }

  protected createFromForm(): ICommentMaster {
    return {
      ...new CommentMaster(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      status: this.editForm.get(['status'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
      dakMaster: this.editForm.get(['dakMaster'])!.value,
    };
  }
}
