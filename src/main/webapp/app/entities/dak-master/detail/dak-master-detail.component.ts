import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDakMaster } from '../dak-master.model';

@Component({
  selector: 'jhi-dak-master-detail',
  templateUrl: './dak-master-detail.component.html',
})
export class DakMasterDetailComponent implements OnInit {
  dakMaster: IDakMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dakMaster }) => {
      this.dakMaster = dakMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
