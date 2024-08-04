import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGhoshwara, getGhoshwaraIdentifier } from '../ghoshwara.model';

export type EntityResponseType = HttpResponse<IGhoshwara>;
export type EntityArrayResponseType = HttpResponse<IGhoshwara[]>;

@Injectable({ providedIn: 'root' })
export class GhoshwaraService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ghoshwaras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ghoshwara: IGhoshwara): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ghoshwara);
    return this.http
      .post<IGhoshwara>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ghoshwara: IGhoshwara): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ghoshwara);
    return this.http
      .put<IGhoshwara>(`${this.resourceUrl}/${getGhoshwaraIdentifier(ghoshwara) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(ghoshwara: IGhoshwara): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ghoshwara);
    return this.http
      .patch<IGhoshwara>(`${this.resourceUrl}/${getGhoshwaraIdentifier(ghoshwara) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGhoshwara>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGhoshwara[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGhoshwaraToCollectionIfMissing(
    ghoshwaraCollection: IGhoshwara[],
    ...ghoshwarasToCheck: (IGhoshwara | null | undefined)[]
  ): IGhoshwara[] {
    const ghoshwaras: IGhoshwara[] = ghoshwarasToCheck.filter(isPresent);
    if (ghoshwaras.length > 0) {
      const ghoshwaraCollectionIdentifiers = ghoshwaraCollection.map(ghoshwaraItem => getGhoshwaraIdentifier(ghoshwaraItem)!);
      const ghoshwarasToAdd = ghoshwaras.filter(ghoshwaraItem => {
        const ghoshwaraIdentifier = getGhoshwaraIdentifier(ghoshwaraItem);
        if (ghoshwaraIdentifier == null || ghoshwaraCollectionIdentifiers.includes(ghoshwaraIdentifier)) {
          return false;
        }
        ghoshwaraCollectionIdentifiers.push(ghoshwaraIdentifier);
        return true;
      });
      return [...ghoshwarasToAdd, ...ghoshwaraCollection];
    }
    return ghoshwaraCollection;
  }

  protected convertDateFromClient(ghoshwara: IGhoshwara): IGhoshwara {
    return Object.assign({}, ghoshwara, {
      date: ghoshwara.date?.isValid() ? ghoshwara.date.toJSON() : undefined,
      lastModified: ghoshwara.lastModified?.isValid() ? ghoshwara.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ghoshwara: IGhoshwara) => {
        ghoshwara.date = ghoshwara.date ? dayjs(ghoshwara.date) : undefined;
        ghoshwara.lastModified = ghoshwara.lastModified ? dayjs(ghoshwara.lastModified) : undefined;
      });
    }
    return res;
  }
}
