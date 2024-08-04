import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDakJourney, getDakJourneyIdentifier } from '../dak-journey.model';

export type EntityResponseType = HttpResponse<IDakJourney>;
export type EntityArrayResponseType = HttpResponse<IDakJourney[]>;

@Injectable({ providedIn: 'root' })
export class DakJourneyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dak-journeys');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dakJourney: IDakJourney): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dakJourney);
    return this.http
      .post<IDakJourney>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dakJourney: IDakJourney): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dakJourney);
    return this.http
      .put<IDakJourney>(`${this.resourceUrl}/${getDakJourneyIdentifier(dakJourney) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dakJourney: IDakJourney): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dakJourney);
    return this.http
      .patch<IDakJourney>(`${this.resourceUrl}/${getDakJourneyIdentifier(dakJourney) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDakJourney>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDakJourney[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDakJourneyToCollectionIfMissing(
    dakJourneyCollection: IDakJourney[],
    ...dakJourneysToCheck: (IDakJourney | null | undefined)[]
  ): IDakJourney[] {
    const dakJourneys: IDakJourney[] = dakJourneysToCheck.filter(isPresent);
    if (dakJourneys.length > 0) {
      const dakJourneyCollectionIdentifiers = dakJourneyCollection.map(dakJourneyItem => getDakJourneyIdentifier(dakJourneyItem)!);
      const dakJourneysToAdd = dakJourneys.filter(dakJourneyItem => {
        const dakJourneyIdentifier = getDakJourneyIdentifier(dakJourneyItem);
        if (dakJourneyIdentifier == null || dakJourneyCollectionIdentifiers.includes(dakJourneyIdentifier)) {
          return false;
        }
        dakJourneyCollectionIdentifiers.push(dakJourneyIdentifier);
        return true;
      });
      return [...dakJourneysToAdd, ...dakJourneyCollection];
    }
    return dakJourneyCollection;
  }

  protected convertDateFromClient(dakJourney: IDakJourney): IDakJourney {
    return Object.assign({}, dakJourney, {
      assignedOn: dakJourney.assignedOn?.isValid() ? dakJourney.assignedOn.toJSON() : undefined,
      updatedOn: dakJourney.updatedOn?.isValid() ? dakJourney.updatedOn.toJSON() : undefined,
      lastModified: dakJourney.lastModified?.isValid() ? dakJourney.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.assignedOn = res.body.assignedOn ? dayjs(res.body.assignedOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dakJourney: IDakJourney) => {
        dakJourney.assignedOn = dakJourney.assignedOn ? dayjs(dakJourney.assignedOn) : undefined;
        dakJourney.updatedOn = dakJourney.updatedOn ? dayjs(dakJourney.updatedOn) : undefined;
        dakJourney.lastModified = dakJourney.lastModified ? dayjs(dakJourney.lastModified) : undefined;
      });
    }
    return res;
  }
}
