import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHearingDetails, HearingDetails } from '../hearing-details.model';
import { HearingDetailsService } from '../service/hearing-details.service';

@Injectable({ providedIn: 'root' })
export class HearingDetailsRoutingResolveService implements Resolve<IHearingDetails> {
  constructor(protected service: HearingDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHearingDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hearingDetails: HttpResponse<HearingDetails>) => {
          if (hearingDetails.body) {
            return of(hearingDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HearingDetails());
  }
}
