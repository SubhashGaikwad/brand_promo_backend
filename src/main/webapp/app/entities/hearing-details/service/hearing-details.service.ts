import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHearingDetails, getHearingDetailsIdentifier } from '../hearing-details.model';

export type EntityResponseType = HttpResponse<IHearingDetails>;
export type EntityArrayResponseType = HttpResponse<IHearingDetails[]>;

@Injectable({ providedIn: 'root' })
export class HearingDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hearing-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hearingDetails: IHearingDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hearingDetails);
    return this.http
      .post<IHearingDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hearingDetails: IHearingDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hearingDetails);
    return this.http
      .put<IHearingDetails>(`${this.resourceUrl}/${getHearingDetailsIdentifier(hearingDetails) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(hearingDetails: IHearingDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hearingDetails);
    return this.http
      .patch<IHearingDetails>(`${this.resourceUrl}/${getHearingDetailsIdentifier(hearingDetails) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHearingDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHearingDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHearingDetailsToCollectionIfMissing(
    hearingDetailsCollection: IHearingDetails[],
    ...hearingDetailsToCheck: (IHearingDetails | null | undefined)[]
  ): IHearingDetails[] {
    const hearingDetails: IHearingDetails[] = hearingDetailsToCheck.filter(isPresent);
    if (hearingDetails.length > 0) {
      const hearingDetailsCollectionIdentifiers = hearingDetailsCollection.map(
        hearingDetailsItem => getHearingDetailsIdentifier(hearingDetailsItem)!
      );
      const hearingDetailsToAdd = hearingDetails.filter(hearingDetailsItem => {
        const hearingDetailsIdentifier = getHearingDetailsIdentifier(hearingDetailsItem);
        if (hearingDetailsIdentifier == null || hearingDetailsCollectionIdentifiers.includes(hearingDetailsIdentifier)) {
          return false;
        }
        hearingDetailsCollectionIdentifiers.push(hearingDetailsIdentifier);
        return true;
      });
      return [...hearingDetailsToAdd, ...hearingDetailsCollection];
    }
    return hearingDetailsCollection;
  }

  protected convertDateFromClient(hearingDetails: IHearingDetails): IHearingDetails {
    return Object.assign({}, hearingDetails, {
      orderDate: hearingDetails.orderDate?.isValid() ? hearingDetails.orderDate.toJSON() : undefined,
      date: hearingDetails.date?.isValid() ? hearingDetails.date.toJSON() : undefined,
      time: hearingDetails.time?.isValid() ? hearingDetails.time.toJSON() : undefined,
      lastModified: hearingDetails.lastModified?.isValid() ? hearingDetails.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.orderDate = res.body.orderDate ? dayjs(res.body.orderDate) : undefined;
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
      res.body.time = res.body.time ? dayjs(res.body.time) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((hearingDetails: IHearingDetails) => {
        hearingDetails.orderDate = hearingDetails.orderDate ? dayjs(hearingDetails.orderDate) : undefined;
        hearingDetails.date = hearingDetails.date ? dayjs(hearingDetails.date) : undefined;
        hearingDetails.time = hearingDetails.time ? dayjs(hearingDetails.time) : undefined;
        hearingDetails.lastModified = hearingDetails.lastModified ? dayjs(hearingDetails.lastModified) : undefined;
      });
    }
    return res;
  }
}
