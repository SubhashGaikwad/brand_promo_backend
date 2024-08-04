import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecurityRole, getSecurityRoleIdentifier } from '../security-role.model';

export type EntityResponseType = HttpResponse<ISecurityRole>;
export type EntityArrayResponseType = HttpResponse<ISecurityRole[]>;

@Injectable({ providedIn: 'root' })
export class SecurityRoleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/security-roles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(securityRole: ISecurityRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityRole);
    return this.http
      .post<ISecurityRole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(securityRole: ISecurityRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityRole);
    return this.http
      .put<ISecurityRole>(`${this.resourceUrl}/${getSecurityRoleIdentifier(securityRole) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(securityRole: ISecurityRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityRole);
    return this.http
      .patch<ISecurityRole>(`${this.resourceUrl}/${getSecurityRoleIdentifier(securityRole) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISecurityRole>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISecurityRole[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSecurityRoleToCollectionIfMissing(
    securityRoleCollection: ISecurityRole[],
    ...securityRolesToCheck: (ISecurityRole | null | undefined)[]
  ): ISecurityRole[] {
    const securityRoles: ISecurityRole[] = securityRolesToCheck.filter(isPresent);
    if (securityRoles.length > 0) {
      const securityRoleCollectionIdentifiers = securityRoleCollection.map(
        securityRoleItem => getSecurityRoleIdentifier(securityRoleItem)!
      );
      const securityRolesToAdd = securityRoles.filter(securityRoleItem => {
        const securityRoleIdentifier = getSecurityRoleIdentifier(securityRoleItem);
        if (securityRoleIdentifier == null || securityRoleCollectionIdentifiers.includes(securityRoleIdentifier)) {
          return false;
        }
        securityRoleCollectionIdentifiers.push(securityRoleIdentifier);
        return true;
      });
      return [...securityRolesToAdd, ...securityRoleCollection];
    }
    return securityRoleCollection;
  }

  protected convertDateFromClient(securityRole: ISecurityRole): ISecurityRole {
    return Object.assign({}, securityRole, {
      lastModified: securityRole.lastModified?.isValid() ? securityRole.lastModified.toJSON() : undefined,
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
      res.body.forEach((securityRole: ISecurityRole) => {
        securityRole.lastModified = securityRole.lastModified ? dayjs(securityRole.lastModified) : undefined;
      });
    }
    return res;
  }
}
