import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDakHistory, DakHistory } from '../dak-history.model';
import { DakHistoryService } from '../service/dak-history.service';

@Injectable({ providedIn: 'root' })
export class DakHistoryRoutingResolveService implements Resolve<IDakHistory> {
  constructor(protected service: DakHistoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDakHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dakHistory: HttpResponse<DakHistory>) => {
          if (dakHistory.body) {
            return of(dakHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DakHistory());
  }
}
