import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISecurityPermission, SecurityPermission } from '../security-permission.model';
import { SecurityPermissionService } from '../service/security-permission.service';

@Component({
  selector: 'jhi-security-permission-update',
  templateUrl: './security-permission-update.component.html',
})
export class SecurityPermissionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    permissionName: [null, [Validators.required]],
    description: [],
    lastModified: [],
    lastModifiedBy: [],
  });

  constructor(
    protected securityPermissionService: SecurityPermissionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityPermission }) => {
      if (securityPermission.id === undefined) {
        const today = dayjs().startOf('day');
        securityPermission.lastModified = today;
      }

      this.updateForm(securityPermission);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityPermission = this.createFromForm();
    if (securityPermission.id !== undefined) {
      this.subscribeToSaveResponse(this.securityPermissionService.update(securityPermission));
    } else {
      this.subscribeToSaveResponse(this.securityPermissionService.create(securityPermission));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityPermission>>): void {
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

  protected updateForm(securityPermission: ISecurityPermission): void {
    this.editForm.patchValue({
      id: securityPermission.id,
      permissionName: securityPermission.permissionName,
      description: securityPermission.description,
      lastModified: securityPermission.lastModified ? securityPermission.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: securityPermission.lastModifiedBy,
    });
  }

  protected createFromForm(): ISecurityPermission {
    return {
      ...new SecurityPermission(),
      id: this.editForm.get(['id'])!.value,
      permissionName: this.editForm.get(['permissionName'])!.value,
      description: this.editForm.get(['description'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
