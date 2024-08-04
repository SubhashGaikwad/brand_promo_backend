import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHearingDetails, HearingDetails } from '../hearing-details.model';
import { HearingDetailsService } from '../service/hearing-details.service';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { DakMasterService } from 'app/entities/dak-master/service/dak-master.service';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';

@Component({
  selector: 'jhi-hearing-details-update',
  templateUrl: './hearing-details-update.component.html',
})
export class HearingDetailsUpdateComponent implements OnInit {
  isSaving = false;
  dakStatusValues = Object.keys(DakStatus);

  dakMastersSharedCollection: IDakMaster[] = [];

  editForm = this.fb.group({
    id: [],
    accuserName: [],
    orderDate: [],
    respondentName: [],
    comment: [],
    date: [],
    time: [],
    status: [],
    lastModified: [],
    lastModifiedBy: [],
    dakMaster: [],
  });

  constructor(
    protected hearingDetailsService: HearingDetailsService,
    protected dakMasterService: DakMasterService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hearingDetails }) => {
      if (hearingDetails.id === undefined) {
        const today = dayjs().startOf('day');
        hearingDetails.orderDate = today;
        hearingDetails.date = today;
        hearingDetails.time = today;
        hearingDetails.lastModified = today;
      }

      this.updateForm(hearingDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hearingDetails = this.createFromForm();
    if (hearingDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.hearingDetailsService.update(hearingDetails));
    } else {
      this.subscribeToSaveResponse(this.hearingDetailsService.create(hearingDetails));
    }
  }

  trackDakMasterById(_index: number, item: IDakMaster): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHearingDetails>>): void {
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

  protected updateForm(hearingDetails: IHearingDetails): void {
    this.editForm.patchValue({
      id: hearingDetails.id,
      accuserName: hearingDetails.accuserName,
      orderDate: hearingDetails.orderDate ? hearingDetails.orderDate.format(DATE_TIME_FORMAT) : null,
      respondentName: hearingDetails.respondentName,
      comment: hearingDetails.comment,
      date: hearingDetails.date ? hearingDetails.date.format(DATE_TIME_FORMAT) : null,
      time: hearingDetails.time ? hearingDetails.time.format(DATE_TIME_FORMAT) : null,
      status: hearingDetails.status,
      lastModified: hearingDetails.lastModified ? hearingDetails.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: hearingDetails.lastModifiedBy,
      dakMaster: hearingDetails.dakMaster,
    });

    this.dakMastersSharedCollection = this.dakMasterService.addDakMasterToCollectionIfMissing(
      this.dakMastersSharedCollection,
      hearingDetails.dakMaster
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
  }

  protected createFromForm(): IHearingDetails {
    return {
      ...new HearingDetails(),
      id: this.editForm.get(['id'])!.value,
      accuserName: this.editForm.get(['accuserName'])!.value,
      orderDate: this.editForm.get(['orderDate'])!.value ? dayjs(this.editForm.get(['orderDate'])!.value, DATE_TIME_FORMAT) : undefined,
      respondentName: this.editForm.get(['respondentName'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      time: this.editForm.get(['time'])!.value ? dayjs(this.editForm.get(['time'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      dakMaster: this.editForm.get(['dakMaster'])!.value,
    };
  }
}
