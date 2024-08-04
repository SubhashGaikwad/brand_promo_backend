import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IParameterLookup, ParameterLookup } from '../parameter-lookup.model';
import { ParameterLookupService } from '../service/parameter-lookup.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

@Component({
  selector: 'jhi-parameter-lookup-update',
  templateUrl: './parameter-lookup-update.component.html',
})
export class ParameterLookupUpdateComponent implements OnInit {
  isSaving = false;

  organizationsSharedCollection: IOrganization[] = [];

  editForm = this.fb.group({
    id: [],
    parameterName: [null, [Validators.required]],
    parameterValue: [null, [Validators.required]],
    type: [null, [Validators.required]],
    status: [],
    lastModified: [],
    lastModifiedBy: [],
    createdBy: [],
    createdOn: [],
    organization: [],
  });

  constructor(
    protected parameterLookupService: ParameterLookupService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameterLookup }) => {
      if (parameterLookup.id === undefined) {
        const today = dayjs().startOf('day');
        parameterLookup.lastModified = today;
        parameterLookup.createdOn = today;
      }

      this.updateForm(parameterLookup);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parameterLookup = this.createFromForm();
    if (parameterLookup.id !== undefined) {
      this.subscribeToSaveResponse(this.parameterLookupService.update(parameterLookup));
    } else {
      this.subscribeToSaveResponse(this.parameterLookupService.create(parameterLookup));
    }
  }

  trackOrganizationById(_index: number, item: IOrganization): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParameterLookup>>): void {
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

  protected updateForm(parameterLookup: IParameterLookup): void {
    this.editForm.patchValue({
      id: parameterLookup.id,
      parameterName: parameterLookup.parameterName,
      parameterValue: parameterLookup.parameterValue,
      type: parameterLookup.type,
      status: parameterLookup.status,
      lastModified: parameterLookup.lastModified ? parameterLookup.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: parameterLookup.lastModifiedBy,
      createdBy: parameterLookup.createdBy,
      createdOn: parameterLookup.createdOn ? parameterLookup.createdOn.format(DATE_TIME_FORMAT) : null,
      organization: parameterLookup.organization,
    });

    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsSharedCollection,
      parameterLookup.organization
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
  }

  protected createFromForm(): IParameterLookup {
    return {
      ...new ParameterLookup(),
      id: this.editForm.get(['id'])!.value,
      parameterName: this.editForm.get(['parameterName'])!.value,
      parameterValue: this.editForm.get(['parameterValue'])!.value,
      type: this.editForm.get(['type'])!.value,
      status: this.editForm.get(['status'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      organization: this.editForm.get(['organization'])!.value,
    };
  }
}
