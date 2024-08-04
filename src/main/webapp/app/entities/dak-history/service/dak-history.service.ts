import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDakHistory, getDakHistoryIdentifier } from '../dak-history.model';

export type EntityResponseType = HttpResponse<IDakHistory>;
export type EntityArrayResponseType = HttpResponse<IDakHistory[]>;

@Injectable({ providedIn: 'root' })
export class DakHistoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dak-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dakHistory: IDakHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dakHistory);
    return this.http
      .post<IDakHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dakHistory: IDakHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dakHistory);
    return this.http
      .put<IDakHistory>(`${this.resourceUrl}/${getDakHistoryIdentifier(dakHistory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dakHistory: IDakHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dakHistory);
    return this.http
      .patch<IDakHistory>(`${this.resourceUrl}/${getDakHistoryIdentifier(dakHistory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDakHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDakHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDakHistoryToCollectionIfMissing(
    dakHistoryCollection: IDakHistory[],
    ...dakHistoriesToCheck: (IDakHistory | null | undefined)[]
  ): IDakHistory[] {
    const dakHistories: IDakHistory[] = dakHistoriesToCheck.filter(isPresent);
    if (dakHistories.length > 0) {
      const dakHistoryCollectionIdentifiers = dakHistoryCollection.map(dakHistoryItem => getDakHistoryIdentifier(dakHistoryItem)!);
      const dakHistoriesToAdd = dakHistories.filter(dakHistoryItem => {
        const dakHistoryIdentifier = getDakHistoryIdentifier(dakHistoryItem);
        if (dakHistoryIdentifier == null || dakHistoryCollectionIdentifiers.includes(dakHistoryIdentifier)) {
          return false;
        }
        dakHistoryCollectionIdentifiers.push(dakHistoryIdentifier);
        return true;
      });
      return [...dakHistoriesToAdd, ...dakHistoryCollection];
    }
    return dakHistoryCollection;
  }

  protected convertDateFromClient(dakHistory: IDakHistory): IDakHistory {
    return Object.assign({}, dakHistory, {
      date: dakHistory.date?.isValid() ? dakHistory.date.toJSON() : undefined,
      assignedOn: dakHistory.assignedOn?.isValid() ? dakHistory.assignedOn.toJSON() : undefined,
      createdOn: dakHistory.createdOn?.isValid() ? dakHistory.createdOn.toJSON() : undefined,
      lastModified: dakHistory.lastModified?.isValid() ? dakHistory.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
      res.body.assignedOn = res.body.assignedOn ? dayjs(res.body.assignedOn) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dakHistory: IDakHistory) => {
        dakHistory.date = dakHistory.date ? dayjs(dakHistory.date) : undefined;
        dakHistory.assignedOn = dakHistory.assignedOn ? dayjs(dakHistory.assignedOn) : undefined;
        dakHistory.createdOn = dakHistory.createdOn ? dayjs(dakHistory.createdOn) : undefined;
        dakHistory.lastModified = dakHistory.lastModified ? dayjs(dakHistory.lastModified) : undefined;
      });
    }
    return res;
  }
}
