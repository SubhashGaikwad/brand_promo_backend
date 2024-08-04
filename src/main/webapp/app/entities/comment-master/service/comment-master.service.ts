import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommentMaster, getCommentMasterIdentifier } from '../comment-master.model';

export type EntityResponseType = HttpResponse<ICommentMaster>;
export type EntityArrayResponseType = HttpResponse<ICommentMaster[]>;

@Injectable({ providedIn: 'root' })
export class CommentMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/comment-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(commentMaster: ICommentMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentMaster);
    return this.http
      .post<ICommentMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commentMaster: ICommentMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentMaster);
    return this.http
      .put<ICommentMaster>(`${this.resourceUrl}/${getCommentMasterIdentifier(commentMaster) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(commentMaster: ICommentMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentMaster);
    return this.http
      .patch<ICommentMaster>(`${this.resourceUrl}/${getCommentMasterIdentifier(commentMaster) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommentMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommentMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCommentMasterToCollectionIfMissing(
    commentMasterCollection: ICommentMaster[],
    ...commentMastersToCheck: (ICommentMaster | null | undefined)[]
  ): ICommentMaster[] {
    const commentMasters: ICommentMaster[] = commentMastersToCheck.filter(isPresent);
    if (commentMasters.length > 0) {
      const commentMasterCollectionIdentifiers = commentMasterCollection.map(
        commentMasterItem => getCommentMasterIdentifier(commentMasterItem)!
      );
      const commentMastersToAdd = commentMasters.filter(commentMasterItem => {
        const commentMasterIdentifier = getCommentMasterIdentifier(commentMasterItem);
        if (commentMasterIdentifier == null || commentMasterCollectionIdentifiers.includes(commentMasterIdentifier)) {
          return false;
        }
        commentMasterCollectionIdentifiers.push(commentMasterIdentifier);
        return true;
      });
      return [...commentMastersToAdd, ...commentMasterCollection];
    }
    return commentMasterCollection;
  }

  protected convertDateFromClient(commentMaster: ICommentMaster): ICommentMaster {
    return Object.assign({}, commentMaster, {
      createdOn: commentMaster.createdOn?.isValid() ? commentMaster.createdOn.toJSON() : undefined,
      lastModified: commentMaster.lastModified?.isValid() ? commentMaster.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commentMaster: ICommentMaster) => {
        commentMaster.createdOn = commentMaster.createdOn ? dayjs(commentMaster.createdOn) : undefined;
        commentMaster.lastModified = commentMaster.lastModified ? dayjs(commentMaster.lastModified) : undefined;
      });
    }
    return res;
  }
}
