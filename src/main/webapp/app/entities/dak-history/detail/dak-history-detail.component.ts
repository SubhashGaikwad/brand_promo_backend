import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDakHistory } from '../dak-history.model';

@Component({
  selector: 'jhi-dak-history-detail',
  templateUrl: './dak-history-detail.component.html',
})
export class DakHistoryDetailComponent implements OnInit {
  dakHistory: IDakHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dakHistory }) => {
      this.dakHistory = dakHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
