import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDakJourney, DakJourney } from '../dak-journey.model';
import { DakJourneyService } from '../service/dak-journey.service';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { DakMasterService } from 'app/entities/dak-master/service/dak-master.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ICommentMaster } from 'app/entities/comment-master/comment-master.model';
import { CommentMasterService } from 'app/entities/comment-master/service/comment-master.service';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';

@Component({
  selector: 'jhi-dak-journey-update',
  templateUrl: './dak-journey-update.component.html',
})
export class DakJourneyUpdateComponent implements OnInit {
  isSaving = false;
  dakStatusValues = Object.keys(DakStatus);

  dakMastersSharedCollection: IDakMaster[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];
  commentMastersSharedCollection: ICommentMaster[] = [];

  editForm = this.fb.group({
    id: [],
    assignedOn: [],
    updatedOn: [],
    dakStatus: [],
    status: [],
    dakAssignedBy: [],
    dakAssignedTo: [],
    lastModified: [],
    lastModifiedBy: [],
    dakMaster: [],
    securityUser: [],
    commentMaster: [],
  });

  constructor(
    protected dakJourneyService: DakJourneyService,
    protected dakMasterService: DakMasterService,
    protected securityUserService: SecurityUserService,
    protected commentMasterService: CommentMasterService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dakJourney }) => {
      if (dakJourney.id === undefined) {
        const today = dayjs().startOf('day');
        dakJourney.assignedOn = today;
        dakJourney.updatedOn = today;
        dakJourney.lastModified = today;
      }

      this.updateForm(dakJourney);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dakJourney = this.createFromForm();
    if (dakJourney.id !== undefined) {
      this.subscribeToSaveResponse(this.dakJourneyService.update(dakJourney));
    } else {
      this.subscribeToSaveResponse(this.dakJourneyService.create(dakJourney));
    }
  }

  trackDakMasterById(_index: number, item: IDakMaster): number {
    return item.id!;
  }

  trackSecurityUserById(_index: number, item: ISecurityUser): number {
    return item.id!;
  }

  trackCommentMasterById(_index: number, item: ICommentMaster): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDakJourney>>): void {
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

  protected updateForm(dakJourney: IDakJourney): void {
    this.editForm.patchValue({
      id: dakJourney.id,
      assignedOn: dakJourney.assignedOn ? dakJourney.assignedOn.format(DATE_TIME_FORMAT) : null,
      updatedOn: dakJourney.updatedOn ? dakJourney.updatedOn.format(DATE_TIME_FORMAT) : null,
      dakStatus: dakJourney.dakStatus,
      status: dakJourney.status,
      dakAssignedBy: dakJourney.dakAssignedBy,
      dakAssignedTo: dakJourney.dakAssignedTo,
      lastModified: dakJourney.lastModified ? dakJourney.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: dakJourney.lastModifiedBy,
      dakMaster: dakJourney.dakMaster,
      securityUser: dakJourney.securityUser,
      commentMaster: dakJourney.commentMaster,
    });

    this.dakMastersSharedCollection = this.dakMasterService.addDakMasterToCollectionIfMissing(
      this.dakMastersSharedCollection,
      dakJourney.dakMaster
    );
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      dakJourney.securityUser
    );
    this.commentMastersSharedCollection = this.commentMasterService.addCommentMasterToCollectionIfMissing(
      this.commentMastersSharedCollection,
      dakJourney.commentMaster
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dakMasterService
      .query()
      .pipe(map((res: HttpResponse<IDakMaster[]>) => res.body ?? []))
      .pipe(
        map((dakMasters: IDakMaster[]) =>
          this.dakMasterService.addDakMasterToCollectionIfMissing(dakMasters, this.editForm.get('dakMaster')!.value)
        )
      )
      .subscribe((dakMasters: IDakMaster[]) => (this.dakMastersSharedCollection = dakMasters));

    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, this.editForm.get('securityUser')!.value)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));

    this.commentMasterService
      .query()
      .pipe(map((res: HttpResponse<ICommentMaster[]>) => res.body ?? []))
      .pipe(
        map((commentMasters: ICommentMaster[]) =>
          this.commentMasterService.addCommentMasterToCollectionIfMissing(commentMasters, this.editForm.get('commentMaster')!.value)
        )
      )
      .subscribe((commentMasters: ICommentMaster[]) => (this.commentMastersSharedCollection = commentMasters));
  }

  protected createFromForm(): IDakJourney {
    return {
      ...new DakJourney(),
      id: this.editForm.get(['id'])!.value,
      assignedOn: this.editForm.get(['assignedOn'])!.value ? dayjs(this.editForm.get(['assignedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      dakStatus: this.editForm.get(['dakStatus'])!.value,
      status: this.editForm.get(['status'])!.value,
      dakAssignedBy: this.editForm.get(['dakAssignedBy'])!.value,
      dakAssignedTo: this.editForm.get(['dakAssignedTo'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      dakMaster: this.editForm.get(['dakMaster'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
      commentMaster: this.editForm.get(['commentMaster'])!.value,
    };
  }
}
