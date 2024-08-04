import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGhoshwara } from '../ghoshwara.model';

@Component({
  selector: 'jhi-ghoshwara-detail',
  templateUrl: './ghoshwara-detail.component.html',
})
export class GhoshwaraDetailComponent implements OnInit {
  ghoshwara: IGhoshwara | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ghoshwara }) => {
      this.ghoshwara = ghoshwara;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
