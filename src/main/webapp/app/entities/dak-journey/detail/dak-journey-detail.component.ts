import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDakJourney } from '../dak-journey.model';

@Component({
  selector: 'jhi-dak-journey-detail',
  templateUrl: './dak-journey-detail.component.html',
})
export class DakJourneyDetailComponent implements OnInit {
  dakJourney: IDakJourney | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dakJourney }) => {
      this.dakJourney = dakJourney;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
