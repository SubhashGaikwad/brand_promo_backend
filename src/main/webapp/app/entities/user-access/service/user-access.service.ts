import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserAccess, getUserAccessIdentifier } from '../user-access.model';

export type EntityResponseType = HttpResponse<IUserAccess>;
export type EntityArrayResponseType = HttpResponse<IUserAccess[]>;

@Injectable({ providedIn: 'root' })
export class UserAccessService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-accesses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userAccess: IUserAccess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userAccess);
    return this.http
      .post<IUserAccess>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userAccess: IUserAccess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userAccess);
    return this.http
      .put<IUserAccess>(`${this.resourceUrl}/${getUserAccessIdentifier(userAccess) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userAccess: IUserAccess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userAccess);
    return this.http
      .patch<IUserAccess>(`${this.resourceUrl}/${getUserAccessIdentifier(userAccess) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserAccess>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserAccess[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserAccessToCollectionIfMissing(
    userAccessCollection: IUserAccess[],
    ...userAccessesToCheck: (IUserAccess | null | undefined)[]
  ): IUserAccess[] {
    const userAccesses: IUserAccess[] = userAccessesToCheck.filter(isPresent);
    if (userAccesses.length > 0) {
      const userAccessCollectionIdentifiers = userAccessCollection.map(userAccessItem => getUserAccessIdentifier(userAccessItem)!);
      const userAccessesToAdd = userAccesses.filter(userAccessItem => {
        const userAccessIdentifier = getUserAccessIdentifier(userAccessItem);
        if (userAccessIdentifier == null || userAccessCollectionIdentifiers.includes(userAccessIdentifier)) {
          return false;
        }
        userAccessCollectionIdentifiers.push(userAccessIdentifier);
        return true;
      });
      return [...userAccessesToAdd, ...userAccessCollection];
    }
    return userAccessCollection;
  }

  protected convertDateFromClient(userAccess: IUserAccess): IUserAccess {
    return Object.assign({}, userAccess, {
      lastModified: userAccess.lastModified?.isValid() ? userAccess.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userAccess: IUserAccess) => {
        userAccess.lastModified = userAccess.lastModified ? dayjs(userAccess.lastModified) : undefined;
      });
    }
    return res;
  }
}
