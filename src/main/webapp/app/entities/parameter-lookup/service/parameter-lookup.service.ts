import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParameterLookup, getParameterLookupIdentifier } from '../parameter-lookup.model';

export type EntityResponseType = HttpResponse<IParameterLookup>;
export type EntityArrayResponseType = HttpResponse<IParameterLookup[]>;

@Injectable({ providedIn: 'root' })
export class ParameterLookupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parameter-lookups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(parameterLookup: IParameterLookup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parameterLookup);
    return this.http
      .post<IParameterLookup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(parameterLookup: IParameterLookup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parameterLookup);
    return this.http
      .put<IParameterLookup>(`${this.resourceUrl}/${getParameterLookupIdentifier(parameterLookup) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(parameterLookup: IParameterLookup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parameterLookup);
    return this.http
      .patch<IParameterLookup>(`${this.resourceUrl}/${getParameterLookupIdentifier(parameterLookup) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParameterLookup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParameterLookup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addParameterLookupToCollectionIfMissing(
    parameterLookupCollection: IParameterLookup[],
    ...parameterLookupsToCheck: (IParameterLookup | null | undefined)[]
  ): IParameterLookup[] {
    const parameterLookups: IParameterLookup[] = parameterLookupsToCheck.filter(isPresent);
    if (parameterLookups.length > 0) {
      const parameterLookupCollectionIdentifiers = parameterLookupCollection.map(
        parameterLookupItem => getParameterLookupIdentifier(parameterLookupItem)!
      );
      const parameterLookupsToAdd = parameterLookups.filter(parameterLookupItem => {
        const parameterLookupIdentifier = getParameterLookupIdentifier(parameterLookupItem);
        if (parameterLookupIdentifier == null || parameterLookupCollectionIdentifiers.includes(parameterLookupIdentifier)) {
          return false;
        }
        parameterLookupCollectionIdentifiers.push(parameterLookupIdentifier);
        return true;
      });
      return [...parameterLookupsToAdd, ...parameterLookupCollection];
    }
    return parameterLookupCollection;
  }

  protected convertDateFromClient(parameterLookup: IParameterLookup): IParameterLookup {
    return Object.assign({}, parameterLookup, {
      lastModified: parameterLookup.lastModified?.isValid() ? parameterLookup.lastModified.toJSON() : undefined,
      createdOn: parameterLookup.createdOn?.isValid() ? parameterLookup.createdOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((parameterLookup: IParameterLookup) => {
        parameterLookup.lastModified = parameterLookup.lastModified ? dayjs(parameterLookup.lastModified) : undefined;
        parameterLookup.createdOn = parameterLookup.createdOn ? dayjs(parameterLookup.createdOn) : undefined;
      });
    }
    return res;
  }
}
