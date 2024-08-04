import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecurityUser } from '../security-user.model';

@Component({
  selector: 'jhi-security-user-detail',
  templateUrl: './security-user-detail.component.html',
})
export class SecurityUserDetailComponent implements OnInit {
  securityUser: ISecurityUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityUser }) => {
      this.securityUser = securityUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
