import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { AccessLevel } from 'app/entities/enumerations/access-level.model';
import { IUserAccess, UserAccess } from '../user-access.model';

import { UserAccessService } from './user-access.service';

describe('UserAccess Service', () => {
  let service: UserAccessService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserAccess;
  let expectedResult: IUserAccess | IUserAccess[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserAccessService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      level: AccessLevel.ROOT,
      accessId: 0,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a UserAccess', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new UserAccess()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserAccess', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          level: 'BBBBBB',
          accessId: 1,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserAccess', () => {
      const patchObject = Object.assign(
        {
          level: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new UserAccess()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserAccess', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          level: 'BBBBBB',
          accessId: 1,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
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

    it('should delete a UserAccess', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserAccessToCollectionIfMissing', () => {
      it('should add a UserAccess to an empty array', () => {
        const userAccess: IUserAccess = { id: 123 };
        expectedResult = service.addUserAccessToCollectionIfMissing([], userAccess);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userAccess);
      });

      it('should not add a UserAccess to an array that contains it', () => {
        const userAccess: IUserAccess = { id: 123 };
        const userAccessCollection: IUserAccess[] = [
          {
            ...userAccess,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserAccessToCollectionIfMissing(userAccessCollection, userAccess);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserAccess to an array that doesn't contain it", () => {
        const userAccess: IUserAccess = { id: 123 };
        const userAccessCollection: IUserAccess[] = [{ id: 456 }];
        expectedResult = service.addUserAccessToCollectionIfMissing(userAccessCollection, userAccess);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userAccess);
      });

      it('should add only unique UserAccess to an array', () => {
        const userAccessArray: IUserAccess[] = [{ id: 123 }, { id: 456 }, { id: 53756 }];
        const userAccessCollection: IUserAccess[] = [{ id: 123 }];
        expectedResult = service.addUserAccessToCollectionIfMissing(userAccessCollection, ...userAccessArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userAccess: IUserAccess = { id: 123 };
        const userAccess2: IUserAccess = { id: 456 };
        expectedResult = service.addUserAccessToCollectionIfMissing([], userAccess, userAccess2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userAccess);
        expect(expectedResult).toContain(userAccess2);
      });

      it('should accept null and undefined values', () => {
        const userAccess: IUserAccess = { id: 123 };
        expectedResult = service.addUserAccessToCollectionIfMissing([], null, userAccess, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userAccess);
      });

      it('should return initial array if no UserAccess is added', () => {
        const userAccessCollection: IUserAccess[] = [{ id: 123 }];
        expectedResult = service.addUserAccessToCollectionIfMissing(userAccessCollection, undefined, null);
        expect(expectedResult).toEqual(userAccessCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
