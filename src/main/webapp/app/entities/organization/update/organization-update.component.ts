import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOrganization, Organization } from '../organization.model';
import { OrganizationService } from '../service/organization.service';
import { OrganizationType } from 'app/entities/enumerations/organization-type.model';

@Component({
  selector: 'jhi-organization-update',
  templateUrl: './organization-update.component.html',
})
export class OrganizationUpdateComponent implements OnInit {
  isSaving = false;
  organizationTypeValues = Object.keys(OrganizationType);

  editForm = this.fb.group({
    id: [],
    organizationName: [null, [Validators.required]],
    address: [],
    createdOn: [],
    description: [],
    isActivate: [],
    orgnizationType: [null, [Validators.required]],
    lastModified: [],
    lastModifiedBy: [],
  });

  constructor(protected organizationService: OrganizationService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organization }) => {
      if (organization.id === undefined) {
        const today = dayjs().startOf('day');
        organization.lastModified = today;
      }

      this.updateForm(organization);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organization = this.createFromForm();
    if (organization.id !== undefined) {
      this.subscribeToSaveResponse(this.organizationService.update(organization));
    } else {
      this.subscribeToSaveResponse(this.organizationService.create(organization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganization>>): void {
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

  protected updateForm(organization: IOrganization): void {
    this.editForm.patchValue({
      id: organization.id,
      organizationName: organization.organizationName,
      address: organization.address,
      createdOn: organization.createdOn,
      description: organization.description,
      isActivate: organization.isActivate,
      orgnizationType: organization.orgnizationType,
      lastModified: organization.lastModified ? organization.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: organization.lastModifiedBy,
    });
  }

  protected createFromForm(): IOrganization {
    return {
      ...new Organization(),
      id: this.editForm.get(['id'])!.value,
      organizationName: this.editForm.get(['organizationName'])!.value,
      address: this.editForm.get(['address'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value,
      description: this.editForm.get(['description'])!.value,
      isActivate: this.editForm.get(['isActivate'])!.value,
      orgnizationType: this.editForm.get(['orgnizationType'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
