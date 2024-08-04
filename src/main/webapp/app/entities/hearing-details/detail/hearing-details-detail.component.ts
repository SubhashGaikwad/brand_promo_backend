import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHearingDetails } from '../hearing-details.model';

@Component({
  selector: 'jhi-hearing-details-detail',
  templateUrl: './hearing-details-detail.component.html',
})
export class HearingDetailsDetailComponent implements OnInit {
  hearingDetails: IHearingDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hearingDetails }) => {
      this.hearingDetails = hearingDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
