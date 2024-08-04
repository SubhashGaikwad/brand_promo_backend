import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDakIdGenerator, getDakIdGeneratorIdentifier } from '../dak-id-generator.model';

export type EntityResponseType = HttpResponse<IDakIdGenerator>;
export type EntityArrayResponseType = HttpResponse<IDakIdGenerator[]>;

@Injectable({ providedIn: 'root' })
export class DakIdGeneratorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dak-id-generators');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dakIdGenerator: IDakIdGenerator): Observable<EntityResponseType> {
    return this.http.post<IDakIdGenerator>(this.resourceUrl, dakIdGenerator, { observe: 'response' });
  }

  update(dakIdGenerator: IDakIdGenerator): Observable<EntityResponseType> {
    return this.http.put<IDakIdGenerator>(`${this.resourceUrl}/${getDakIdGeneratorIdentifier(dakIdGenerator) as number}`, dakIdGenerator, {
      observe: 'response',
    });
  }

  partialUpdate(dakIdGenerator: IDakIdGenerator): Observable<EntityResponseType> {
    return this.http.patch<IDakIdGenerator>(
      `${this.resourceUrl}/${getDakIdGeneratorIdentifier(dakIdGenerator) as number}`,
      dakIdGenerator,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDakIdGenerator>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDakIdGenerator[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDakIdGeneratorToCollectionIfMissing(
    dakIdGeneratorCollection: IDakIdGenerator[],
    ...dakIdGeneratorsToCheck: (IDakIdGenerator | null | undefined)[]
  ): IDakIdGenerator[] {
    const dakIdGenerators: IDakIdGenerator[] = dakIdGeneratorsToCheck.filter(isPresent);
    if (dakIdGenerators.length > 0) {
      const dakIdGeneratorCollectionIdentifiers = dakIdGeneratorCollection.map(
        dakIdGeneratorItem => getDakIdGeneratorIdentifier(dakIdGeneratorItem)!
      );
      const dakIdGeneratorsToAdd = dakIdGenerators.filter(dakIdGeneratorItem => {
        const dakIdGeneratorIdentifier = getDakIdGeneratorIdentifier(dakIdGeneratorItem);
        if (dakIdGeneratorIdentifier == null || dakIdGeneratorCollectionIdentifiers.includes(dakIdGeneratorIdentifier)) {
          return false;
        }
        dakIdGeneratorCollectionIdentifiers.push(dakIdGeneratorIdentifier);
        return true;
      });
      return [...dakIdGeneratorsToAdd, ...dakIdGeneratorCollection];
    }
    return dakIdGeneratorCollection;
  }
}
