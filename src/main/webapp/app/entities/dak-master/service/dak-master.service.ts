import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDakMaster, getDakMasterIdentifier } from '../dak-master.model';

export type EntityResponseType = HttpResponse<IDakMaster>;
export type EntityArrayResponseType = HttpResponse<IDakMaster[]>;

@Injectable({ providedIn: 'root' })
export class DakMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dak-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dakMaster: IDakMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dakMaster);
    return this.http
      .post<IDakMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dakMaster: IDakMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dakMaster);
    return this.http
      .put<IDakMaster>(`${this.resourceUrl}/${getDakMasterIdentifier(dakMaster) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dakMaster: IDakMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dakMaster);
    return this.http
      .patch<IDakMaster>(`${this.resourceUrl}/${getDakMasterIdentifier(dakMaster) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDakMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDakMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDakMasterToCollectionIfMissing(
    dakMasterCollection: IDakMaster[],
    ...dakMastersToCheck: (IDakMaster | null | undefined)[]
  ): IDakMaster[] {
    const dakMasters: IDakMaster[] = dakMastersToCheck.filter(isPresent);
    if (dakMasters.length > 0) {
      const dakMasterCollectionIdentifiers = dakMasterCollection.map(dakMasterItem => getDakMasterIdentifier(dakMasterItem)!);
      const dakMastersToAdd = dakMasters.filter(dakMasterItem => {
        const dakMasterIdentifier = getDakMasterIdentifier(dakMasterItem);
        if (dakMasterIdentifier == null || dakMasterCollectionIdentifiers.includes(dakMasterIdentifier)) {
          return false;
        }
        dakMasterCollectionIdentifiers.push(dakMasterIdentifier);
        return true;
      });
      return [...dakMastersToAdd, ...dakMasterCollection];
    }
    return dakMasterCollection;
  }

  protected convertDateFromClient(dakMaster: IDakMaster): IDakMaster {
    return Object.assign({}, dakMaster, {
      letterDate: dakMaster.letterDate?.isValid() ? dakMaster.letterDate.toJSON() : undefined,
      letterReceivedDate: dakMaster.letterReceivedDate?.isValid() ? dakMaster.letterReceivedDate.toJSON() : undefined,
      dispatchDate: dakMaster.dispatchDate?.isValid() ? dakMaster.dispatchDate.toJSON() : undefined,
      assignedDate: dakMaster.assignedDate?.isValid() ? dakMaster.assignedDate.toJSON() : undefined,
      lastModified: dakMaster.lastModified?.isValid() ? dakMaster.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.letterDate = res.body.letterDate ? dayjs(res.body.letterDate) : undefined;
      res.body.letterReceivedDate = res.body.letterReceivedDate ? dayjs(res.body.letterReceivedDate) : undefined;
      res.body.dispatchDate = res.body.dispatchDate ? dayjs(res.body.dispatchDate) : undefined;
      res.body.assignedDate = res.body.assignedDate ? dayjs(res.body.assignedDate) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dakMaster: IDakMaster) => {
        dakMaster.letterDate = dakMaster.letterDate ? dayjs(dakMaster.letterDate) : undefined;
        dakMaster.letterReceivedDate = dakMaster.letterReceivedDate ? dayjs(dakMaster.letterReceivedDate) : undefined;
        dakMaster.dispatchDate = dakMaster.dispatchDate ? dayjs(dakMaster.dispatchDate) : undefined;
        dakMaster.assignedDate = dakMaster.assignedDate ? dayjs(dakMaster.assignedDate) : undefined;
        dakMaster.lastModified = dakMaster.lastModified ? dayjs(dakMaster.lastModified) : undefined;
      });
    }
    return res;
  }
}
