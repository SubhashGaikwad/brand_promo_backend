import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecurityPermission } from '../security-permission.model';

@Component({
  selector: 'jhi-security-permission-detail',
  templateUrl: './security-permission-detail.component.html',
})
export class SecurityPermissionDetailComponent implements OnInit {
  securityPermission: ISecurityPermission | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityPermission }) => {
      this.securityPermission = securityPermission;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
