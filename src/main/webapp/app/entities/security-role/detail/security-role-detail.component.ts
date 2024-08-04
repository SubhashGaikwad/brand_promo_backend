import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecurityRole } from '../security-role.model';

@Component({
  selector: 'jhi-security-role-detail',
  templateUrl: './security-role-detail.component.html',
})
export class SecurityRoleDetailComponent implements OnInit {
  securityRole: ISecurityRole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityRole }) => {
      this.securityRole = securityRole;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
