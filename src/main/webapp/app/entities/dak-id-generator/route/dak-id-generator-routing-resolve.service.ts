import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDakIdGenerator, DakIdGenerator } from '../dak-id-generator.model';
import { DakIdGeneratorService } from '../service/dak-id-generator.service';

@Injectable({ providedIn: 'root' })
export class DakIdGeneratorRoutingResolveService implements Resolve<IDakIdGenerator> {
  constructor(protected service: DakIdGeneratorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDakIdGenerator> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dakIdGenerator: HttpResponse<DakIdGenerator>) => {
          if (dakIdGenerator.body) {
            return of(dakIdGenerator.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DakIdGenerator());
  }
}
