import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDakHistory, DakHistory } from '../dak-history.model';
import { DakHistoryService } from '../service/dak-history.service';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { DakMasterService } from 'app/entities/dak-master/service/dak-master.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';

@Component({
  selector: 'jhi-dak-history-update',
  templateUrl: './dak-history-update.component.html',
})
export class DakHistoryUpdateComponent implements OnInit {
  isSaving = false;
  dakStatusValues = Object.keys(DakStatus);

  dakMastersSharedCollection: IDakMaster[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    assignedBy: [],
    assignedOn: [],
    createdOn: [],
    dakStatus: [],
    status: [],
    lastModified: [],
    lastModifiedBy: [],
    dakMaster: [],
    securityUser: [],
  });

  constructor(
    protected dakHistoryService: DakHistoryService,
    protected dakMasterService: DakMasterService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dakHistory }) => {
      if (dakHistory.id === undefined) {
        const today = dayjs().startOf('day');
        dakHistory.date = today;
        dakHistory.assignedOn = today;
        dakHistory.createdOn = today;
        dakHistory.lastModified = today;
      }

      this.updateForm(dakHistory);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dakHistory = this.createFromForm();
    if (dakHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.dakHistoryService.update(dakHistory));
    } else {
      this.subscribeToSaveResponse(this.dakHistoryService.create(dakHistory));
    }
  }

  trackDakMasterById(_index: number, item: IDakMaster): number {
    return item.id!;
  }

  trackSecurityUserById(_index: number, item: ISecurityUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDakHistory>>): void {
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

  protected updateForm(dakHistory: IDakHistory): void {
    this.editForm.patchValue({
      id: dakHistory.id,
      date: dakHistory.date ? dakHistory.date.format(DATE_TIME_FORMAT) : null,
      assignedBy: dakHistory.assignedBy,
      assignedOn: dakHistory.assignedOn ? dakHistory.assignedOn.format(DATE_TIME_FORMAT) : null,
      createdOn: dakHistory.createdOn ? dakHistory.createdOn.format(DATE_TIME_FORMAT) : null,
      dakStatus: dakHistory.dakStatus,
      status: dakHistory.status,
      lastModified: dakHistory.lastModified ? dakHistory.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: dakHistory.lastModifiedBy,
      dakMaster: dakHistory.dakMaster,
      securityUser: dakHistory.securityUser,
    });

    this.dakMastersSharedCollection = this.dakMasterService.addDakMasterToCollectionIfMissing(
      this.dakMastersSharedCollection,
      dakHistory.dakMaster
    );
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      dakHistory.securityUser
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
  }

  protected createFromForm(): IDakHistory {
    return {
      ...new DakHistory(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      assignedBy: this.editForm.get(['assignedBy'])!.value,
      assignedOn: this.editForm.get(['assignedOn'])!.value ? dayjs(this.editForm.get(['assignedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      dakStatus: this.editForm.get(['dakStatus'])!.value,
      status: this.editForm.get(['status'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      dakMaster: this.editForm.get(['dakMaster'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
    };
  }
}
