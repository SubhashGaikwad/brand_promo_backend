import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDakMaster, DakMaster } from '../dak-master.model';
import { DakMasterService } from '../service/dak-master.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';
import { LetterType } from 'app/entities/enumerations/letter-type.model';

@Component({
  selector: 'jhi-dak-master-update',
  templateUrl: './dak-master-update.component.html',
})
export class DakMasterUpdateComponent implements OnInit {
  isSaving = false;
  dakStatusValues = Object.keys(DakStatus);
  letterTypeValues = Object.keys(LetterType);

  organizationsSharedCollection: IOrganization[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];

  editForm = this.fb.group({
    id: [],
    inwardNumber: [],
    senderName: [],
    contactNumber: [],
    senderAddress: [],
    senderEmail: [],
    subject: [],
    letterDate: [],
    currentStatus: [],
    letterStatus: [],
    letterReceivedDate: [],
    awaitReason: [],
    dispatchDate: [],
    createdBy: [],
    letterType: [],
    isResponseReceived: [],
    assignedDate: [],
    lastModified: [],
    lastModifiedBy: [],
    dakAssignedFrom: [],
    dakAssignee: [],
    dispatchBy: [],
    senderOutward: [],
    outwardNumber: [],
    taluka: [],
    organization: [],
    securityUsers: [],
  });

  constructor(
    protected dakMasterService: DakMasterService,
    protected organizationService: OrganizationService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dakMaster }) => {
      if (dakMaster.id === undefined) {
        const today = dayjs().startOf('day');
        dakMaster.letterDate = today;
        dakMaster.letterReceivedDate = today;
        dakMaster.dispatchDate = today;
        dakMaster.assignedDate = today;
        dakMaster.lastModified = today;
      }

      this.updateForm(dakMaster);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dakMaster = this.createFromForm();
    if (dakMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.dakMasterService.update(dakMaster));
    } else {
      this.subscribeToSaveResponse(this.dakMasterService.create(dakMaster));
    }
  }

  trackOrganizationById(_index: number, item: IOrganization): number {
    return item.id!;
  }

  trackSecurityUserById(_index: number, item: ISecurityUser): number {
    return item.id!;
  }

  getSelectedSecurityUser(option: ISecurityUser, selectedVals?: ISecurityUser[]): ISecurityUser {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDakMaster>>): void {
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

  protected updateForm(dakMaster: IDakMaster): void {
    this.editForm.patchValue({
      id: dakMaster.id,
      inwardNumber: dakMaster.inwardNumber,
      senderName: dakMaster.senderName,
      contactNumber: dakMaster.contactNumber,
      senderAddress: dakMaster.senderAddress,
      senderEmail: dakMaster.senderEmail,
      subject: dakMaster.subject,
      letterDate: dakMaster.letterDate ? dakMaster.letterDate.format(DATE_TIME_FORMAT) : null,
      currentStatus: dakMaster.currentStatus,
      letterStatus: dakMaster.letterStatus,
      letterReceivedDate: dakMaster.letterReceivedDate ? dakMaster.letterReceivedDate.format(DATE_TIME_FORMAT) : null,
      awaitReason: dakMaster.awaitReason,
      dispatchDate: dakMaster.dispatchDate ? dakMaster.dispatchDate.format(DATE_TIME_FORMAT) : null,
      createdBy: dakMaster.createdBy,
      letterType: dakMaster.letterType,
      isResponseReceived: dakMaster.isResponseReceived,
      assignedDate: dakMaster.assignedDate ? dakMaster.assignedDate.format(DATE_TIME_FORMAT) : null,
      lastModified: dakMaster.lastModified ? dakMaster.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: dakMaster.lastModifiedBy,
      dakAssignedFrom: dakMaster.dakAssignedFrom,
      dakAssignee: dakMaster.dakAssignee,
      dispatchBy: dakMaster.dispatchBy,
      senderOutward: dakMaster.senderOutward,
      outwardNumber: dakMaster.outwardNumber,
      taluka: dakMaster.taluka,
      organization: dakMaster.organization,
      securityUsers: dakMaster.securityUsers,
    });

    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsSharedCollection,
      dakMaster.organization
    );
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      ...(dakMaster.securityUsers ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing(organizations, this.editForm.get('organization')!.value)
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));

    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, ...(this.editForm.get('securityUsers')!.value ?? []))
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));
  }

  protected createFromForm(): IDakMaster {
    return {
      ...new DakMaster(),
      id: this.editForm.get(['id'])!.value,
      inwardNumber: this.editForm.get(['inwardNumber'])!.value,
      senderName: this.editForm.get(['senderName'])!.value,
      contactNumber: this.editForm.get(['contactNumber'])!.value,
      senderAddress: this.editForm.get(['senderAddress'])!.value,
      senderEmail: this.editForm.get(['senderEmail'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      letterDate: this.editForm.get(['letterDate'])!.value ? dayjs(this.editForm.get(['letterDate'])!.value, DATE_TIME_FORMAT) : undefined,
      currentStatus: this.editForm.get(['currentStatus'])!.value,
      letterStatus: this.editForm.get(['letterStatus'])!.value,
      letterReceivedDate: this.editForm.get(['letterReceivedDate'])!.value
        ? dayjs(this.editForm.get(['letterReceivedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      awaitReason: this.editForm.get(['awaitReason'])!.value,
      dispatchDate: this.editForm.get(['dispatchDate'])!.value
        ? dayjs(this.editForm.get(['dispatchDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      letterType: this.editForm.get(['letterType'])!.value,
      isResponseReceived: this.editForm.get(['isResponseReceived'])!.value,
      assignedDate: this.editForm.get(['assignedDate'])!.value
        ? dayjs(this.editForm.get(['assignedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      dakAssignedFrom: this.editForm.get(['dakAssignedFrom'])!.value,
      dakAssignee: this.editForm.get(['dakAssignee'])!.value,
      dispatchBy: this.editForm.get(['dispatchBy'])!.value,
      senderOutward: this.editForm.get(['senderOutward'])!.value,
      outwardNumber: this.editForm.get(['outwardNumber'])!.value,
      taluka: this.editForm.get(['taluka'])!.value,
      organization: this.editForm.get(['organization'])!.value,
      securityUsers: this.editForm.get(['securityUsers'])!.value,
    };
  }
}
