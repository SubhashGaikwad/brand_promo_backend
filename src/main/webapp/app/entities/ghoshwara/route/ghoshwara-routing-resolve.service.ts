import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGhoshwara, Ghoshwara } from '../ghoshwara.model';
import { GhoshwaraService } from '../service/ghoshwara.service';

@Injectable({ providedIn: 'root' })
export class GhoshwaraRoutingResolveService implements Resolve<IGhoshwara> {
  constructor(protected service: GhoshwaraService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGhoshwara> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ghoshwara: HttpResponse<Ghoshwara>) => {
          if (ghoshwara.body) {
            return of(ghoshwara.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ghoshwara());
  }
}
