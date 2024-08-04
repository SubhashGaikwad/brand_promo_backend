import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommentMaster, CommentMaster } from '../comment-master.model';
import { CommentMasterService } from '../service/comment-master.service';

@Injectable({ providedIn: 'root' })
export class CommentMasterRoutingResolveService implements Resolve<ICommentMaster> {
  constructor(protected service: CommentMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommentMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((commentMaster: HttpResponse<CommentMaster>) => {
          if (commentMaster.body) {
            return of(commentMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommentMaster());
  }
}
