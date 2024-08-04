import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDakIdGenerator, DakIdGenerator } from '../dak-id-generator.model';
import { DakIdGeneratorService } from '../service/dak-id-generator.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

@Component({
  selector: 'jhi-dak-id-generator-update',
  templateUrl: './dak-id-generator-update.component.html',
})
export class DakIdGeneratorUpdateComponent implements OnInit {
  isSaving = false;

  organizationsCollection: IOrganization[] = [];

  editForm = this.fb.group({
    id: [],
    nextValInward: [],
    nextValOutward: [],
    organization: [],
  });

  constructor(
    protected dakIdGeneratorService: DakIdGeneratorService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dakIdGenerator }) => {
      this.updateForm(dakIdGenerator);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dakIdGenerator = this.createFromForm();
    if (dakIdGenerator.id !== undefined) {
      this.subscribeToSaveResponse(this.dakIdGeneratorService.update(dakIdGenerator));
    } else {
      this.subscribeToSaveResponse(this.dakIdGeneratorService.create(dakIdGenerator));
    }
  }

  trackOrganizationById(_index: number, item: IOrganization): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDakIdGenerator>>): void {
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

  protected updateForm(dakIdGenerator: IDakIdGenerator): void {
    this.editForm.patchValue({
      id: dakIdGenerator.id,
      nextValInward: dakIdGenerator.nextValInward,
      nextValOutward: dakIdGenerator.nextValOutward,
      organization: dakIdGenerator.organization,
    });

    this.organizationsCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsCollection,
      dakIdGenerator.organization
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organizationService
      .query({ 'dakIdGeneratorId.specified': 'false' })
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing(organizations, this.editForm.get('organization')!.value)
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsCollection = organizations));
  }

  protected createFromForm(): IDakIdGenerator {
    return {
      ...new DakIdGenerator(),
      id: this.editForm.get(['id'])!.value,
      nextValInward: this.editForm.get(['nextValInward'])!.value,
      nextValOutward: this.editForm.get(['nextValOutward'])!.value,
      organization: this.editForm.get(['organization'])!.value,
    };
  }
}
