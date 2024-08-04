import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecurityUser, getSecurityUserIdentifier } from '../security-user.model';

export type EntityResponseType = HttpResponse<ISecurityUser>;
export type EntityArrayResponseType = HttpResponse<ISecurityUser[]>;

@Injectable({ providedIn: 'root' })
export class SecurityUserService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/security-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(securityUser: ISecurityUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityUser);
    return this.http
      .post<ISecurityUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(securityUser: ISecurityUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityUser);
    return this.http
      .put<ISecurityUser>(`${this.resourceUrl}/${getSecurityUserIdentifier(securityUser) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(securityUser: ISecurityUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityUser);
    return this.http
      .patch<ISecurityUser>(`${this.resourceUrl}/${getSecurityUserIdentifier(securityUser) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISecurityUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISecurityUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSecurityUserToCollectionIfMissing(
    securityUserCollection: ISecurityUser[],
    ...securityUsersToCheck: (ISecurityUser | null | undefined)[]
  ): ISecurityUser[] {
    const securityUsers: ISecurityUser[] = securityUsersToCheck.filter(isPresent);
    if (securityUsers.length > 0) {
      const securityUserCollectionIdentifiers = securityUserCollection.map(
        securityUserItem => getSecurityUserIdentifier(securityUserItem)!
      );
      const securityUsersToAdd = securityUsers.filter(securityUserItem => {
        const securityUserIdentifier = getSecurityUserIdentifier(securityUserItem);
        if (securityUserIdentifier == null || securityUserCollectionIdentifiers.includes(securityUserIdentifier)) {
          return false;
        }
        securityUserCollectionIdentifiers.push(securityUserIdentifier);
        return true;
      });
      return [...securityUsersToAdd, ...securityUserCollection];
    }
    return securityUserCollection;
  }

  protected convertDateFromClient(securityUser: ISecurityUser): ISecurityUser {
    return Object.assign({}, securityUser, {
      resetDate: securityUser.resetDate?.isValid() ? securityUser.resetDate.toJSON() : undefined,
      createdOn: securityUser.createdOn?.isValid() ? securityUser.createdOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.resetDate = res.body.resetDate ? dayjs(res.body.resetDate) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((securityUser: ISecurityUser) => {
        securityUser.resetDate = securityUser.resetDate ? dayjs(securityUser.resetDate) : undefined;
        securityUser.createdOn = securityUser.createdOn ? dayjs(securityUser.createdOn) : undefined;
      });
    }
    return res;
  }
}
