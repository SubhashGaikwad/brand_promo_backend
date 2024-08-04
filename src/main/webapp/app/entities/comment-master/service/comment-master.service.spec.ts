import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICommentMaster, CommentMaster } from '../comment-master.model';

import { CommentMasterService } from './comment-master.service';

describe('CommentMaster Service', () => {
  let service: CommentMasterService;
  let httpMock: HttpTestingController;
  let elemDefault: ICommentMaster;
  let expectedResult: ICommentMaster | ICommentMaster[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CommentMasterService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      description: 'AAAAAAA',
      createdOn: currentDate,
      createdBy: 'AAAAAAA',
      status: false,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a CommentMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new CommentMaster()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CommentMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          description: 'BBBBBB',
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 'BBBBBB',
          status: true,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CommentMaster', () => {
      const patchObject = Object.assign(
        {
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new CommentMaster()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CommentMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          description: 'BBBBBB',
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 'BBBBBB',
          status: true,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a CommentMaster', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCommentMasterToCollectionIfMissing', () => {
      it('should add a CommentMaster to an empty array', () => {
        const commentMaster: ICommentMaster = { id: 123 };
        expectedResult = service.addCommentMasterToCollectionIfMissing([], commentMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commentMaster);
      });

      it('should not add a CommentMaster to an array that contains it', () => {
        const commentMaster: ICommentMaster = { id: 123 };
        const commentMasterCollection: ICommentMaster[] = [
          {
            ...commentMaster,
          },
          { id: 456 },
        ];
        expectedResult = service.addCommentMasterToCollectionIfMissing(commentMasterCollection, commentMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CommentMaster to an array that doesn't contain it", () => {
        const commentMaster: ICommentMaster = { id: 123 };
        const commentMasterCollection: ICommentMaster[] = [{ id: 456 }];
        expectedResult = service.addCommentMasterToCollectionIfMissing(commentMasterCollection, commentMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commentMaster);
      });

      it('should add only unique CommentMaster to an array', () => {
        const commentMasterArray: ICommentMaster[] = [{ id: 123 }, { id: 456 }, { id: 3715 }];
        const commentMasterCollection: ICommentMaster[] = [{ id: 123 }];
        expectedResult = service.addCommentMasterToCollectionIfMissing(commentMasterCollection, ...commentMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const commentMaster: ICommentMaster = { id: 123 };
        const commentMaster2: ICommentMaster = { id: 456 };
        expectedResult = service.addCommentMasterToCollectionIfMissing([], commentMaster, commentMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commentMaster);
        expect(expectedResult).toContain(commentMaster2);
      });

      it('should accept null and undefined values', () => {
        const commentMaster: ICommentMaster = { id: 123 };
        expectedResult = service.addCommentMasterToCollectionIfMissing([], null, commentMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commentMaster);
      });

      it('should return initial array if no CommentMaster is added', () => {
        const commentMasterCollection: ICommentMaster[] = [{ id: 123 }];
        expectedResult = service.addCommentMasterToCollectionIfMissing(commentMasterCollection, undefined, null);
        expect(expectedResult).toEqual(commentMasterCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
