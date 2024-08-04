import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IGhoshwara, Ghoshwara } from '../ghoshwara.model';
import { GhoshwaraService } from '../service/ghoshwara.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { RegisterType } from 'app/entities/enumerations/register-type.model';

@Component({
  selector: 'jhi-ghoshwara-update',
  templateUrl: './ghoshwara-update.component.html',
})
export class GhoshwaraUpdateComponent implements OnInit {
  isSaving = false;
  registerTypeValues = Object.keys(RegisterType);

  securityUsersSharedCollection: ISecurityUser[] = [];

  editForm = this.fb.group({
    id: [],
    registerType: [],
    initialPendings: [],
    currentWeekInwards: [],
    total: [],
    selfGenerated: [],
    currentWeekCleared: [],
    weeklyPendings: [],
    firstWeek: [],
    secondWeek: [],
    thirdWeek: [],
    firstMonth: [],
    secondMonth: [],
    thirdMonth: [],
    withinSixMonths: [],
    date: [],
    lastModified: [],
    lastModifiedBy: [],
    securityUser: [],
  });

  constructor(
    protected ghoshwaraService: GhoshwaraService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ghoshwara }) => {
      if (ghoshwara.id === undefined) {
        const today = dayjs().startOf('day');
        ghoshwara.date = today;
        ghoshwara.lastModified = today;
      }

      this.updateForm(ghoshwara);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ghoshwara = this.createFromForm();
    if (ghoshwara.id !== undefined) {
      this.subscribeToSaveResponse(this.ghoshwaraService.update(ghoshwara));
    } else {
      this.subscribeToSaveResponse(this.ghoshwaraService.create(ghoshwara));
    }
  }

  trackSecurityUserById(_index: number, item: ISecurityUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGhoshwara>>): void {
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

  protected updateForm(ghoshwara: IGhoshwara): void {
    this.editForm.patchValue({
      id: ghoshwara.id,
      registerType: ghoshwara.registerType,
      initialPendings: ghoshwara.initialPendings,
      currentWeekInwards: ghoshwara.currentWeekInwards,
      total: ghoshwara.total,
      selfGenerated: ghoshwara.selfGenerated,
      currentWeekCleared: ghoshwara.currentWeekCleared,
      weeklyPendings: ghoshwara.weeklyPendings,
      firstWeek: ghoshwara.firstWeek,
      secondWeek: ghoshwara.secondWeek,
      thirdWeek: ghoshwara.thirdWeek,
      firstMonth: ghoshwara.firstMonth,
      secondMonth: ghoshwara.secondMonth,
      thirdMonth: ghoshwara.thirdMonth,
      withinSixMonths: ghoshwara.withinSixMonths,
      date: ghoshwara.date ? ghoshwara.date.format(DATE_TIME_FORMAT) : null,
      lastModified: ghoshwara.lastModified ? ghoshwara.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: ghoshwara.lastModifiedBy,
      securityUser: ghoshwara.securityUser,
    });

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      ghoshwara.securityUser
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

  protected createFromForm(): IGhoshwara {
    return {
      ...new Ghoshwara(),
      id: this.editForm.get(['id'])!.value,
      registerType: this.editForm.get(['registerType'])!.value,
      initialPendings: this.editForm.get(['initialPendings'])!.value,
      currentWeekInwards: this.editForm.get(['currentWeekInwards'])!.value,
      total: this.editForm.get(['total'])!.value,
      selfGenerated: this.editForm.get(['selfGenerated'])!.value,
      currentWeekCleared: this.editForm.get(['currentWeekCleared'])!.value,
      weeklyPendings: this.editForm.get(['weeklyPendings'])!.value,
      firstWeek: this.editForm.get(['firstWeek'])!.value,
      secondWeek: this.editForm.get(['secondWeek'])!.value,
      thirdWeek: this.editForm.get(['thirdWeek'])!.value,
      firstMonth: this.editForm.get(['firstMonth'])!.value,
      secondMonth: this.editForm.get(['secondMonth'])!.value,
      thirdMonth: this.editForm.get(['thirdMonth'])!.value,
      withinSixMonths: this.editForm.get(['withinSixMonths'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
    };
  }
}
