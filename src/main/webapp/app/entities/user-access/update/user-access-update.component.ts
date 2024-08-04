import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserAccess, UserAccess } from '../user-access.model';
import { UserAccessService } from '../service/user-access.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { AccessLevel } from 'app/entities/enumerations/access-level.model';

@Component({
  selector: 'jhi-user-access-update',
  templateUrl: './user-access-update.component.html',
})
export class UserAccessUpdateComponent implements OnInit {
  isSaving = false;
  accessLevelValues = Object.keys(AccessLevel);

  securityUsersSharedCollection: ISecurityUser[] = [];

  editForm = this.fb.group({
    id: [],
    level: [],
    accessId: [],
    lastModified: [],
    lastModifiedBy: [],
    securityUser: [],
  });

  constructor(
    protected userAccessService: UserAccessService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userAccess }) => {
      if (userAccess.id === undefined) {
        const today = dayjs().startOf('day');
        userAccess.lastModified = today;
      }

      this.updateForm(userAccess);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userAccess = this.createFromForm();
    if (userAccess.id !== undefined) {
      this.subscribeToSaveResponse(this.userAccessService.update(userAccess));
    } else {
      this.subscribeToSaveResponse(this.userAccessService.create(userAccess));
    }
  }

  trackSecurityUserById(_index: number, item: ISecurityUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserAccess>>): void {
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

  protected updateForm(userAccess: IUserAccess): void {
    this.editForm.patchValue({
      id: userAccess.id,
      level: userAccess.level,
      accessId: userAccess.accessId,
      lastModified: userAccess.lastModified ? userAccess.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: userAccess.lastModifiedBy,
      securityUser: userAccess.securityUser,
    });

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      userAccess.securityUser
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
  }

  protected createFromForm(): IUserAccess {
    return {
      ...new UserAccess(),
      id: this.editForm.get(['id'])!.value,
      level: this.editForm.get(['level'])!.value,
      accessId: this.editForm.get(['accessId'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
    };
  }
}
