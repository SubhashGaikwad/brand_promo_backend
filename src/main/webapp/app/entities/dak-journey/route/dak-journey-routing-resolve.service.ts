import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDakJourney, DakJourney } from '../dak-journey.model';
import { DakJourneyService } from '../service/dak-journey.service';

@Injectable({ providedIn: 'root' })
export class DakJourneyRoutingResolveService implements Resolve<IDakJourney> {
  constructor(protected service: DakJourneyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDakJourney> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dakJourney: HttpResponse<DakJourney>) => {
          if (dakJourney.body) {
            return of(dakJourney.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DakJourney());
  }
}
