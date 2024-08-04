import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDakIdGenerator } from '../dak-id-generator.model';

@Component({
  selector: 'jhi-dak-id-generator-detail',
  templateUrl: './dak-id-generator-detail.component.html',
})
export class DakIdGeneratorDetailComponent implements OnInit {
  dakIdGenerator: IDakIdGenerator | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dakIdGenerator }) => {
      this.dakIdGenerator = dakIdGenerator;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
