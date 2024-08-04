import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserAccess, UserAccess } from '../user-access.model';
import { UserAccessService } from '../service/user-access.service';

@Injectable({ providedIn: 'root' })
export class UserAccessRoutingResolveService implements Resolve<IUserAccess> {
  constructor(protected service: UserAccessService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserAccess> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userAccess: HttpResponse<UserAccess>) => {
          if (userAccess.body) {
            return of(userAccess.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserAccess());
  }
}
