import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParameterLookup } from '../parameter-lookup.model';

@Component({
  selector: 'jhi-parameter-lookup-detail',
  templateUrl: './parameter-lookup-detail.component.html',
})
export class ParameterLookupDetailComponent implements OnInit {
  parameterLookup: IParameterLookup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameterLookup }) => {
      this.parameterLookup = parameterLookup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
