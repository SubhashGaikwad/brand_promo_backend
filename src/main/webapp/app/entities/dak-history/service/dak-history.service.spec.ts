import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';
import { IDakHistory, DakHistory } from '../dak-history.model';

import { DakHistoryService } from './dak-history.service';

describe('DakHistory Service', () => {
  let service: DakHistoryService;
  let httpMock: HttpTestingController;
  let elemDefault: IDakHistory;
  let expectedResult: IDakHistory | IDakHistory[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DakHistoryService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      date: currentDate,
      assignedBy: 'AAAAAAA',
      assignedOn: currentDate,
      createdOn: currentDate,
      dakStatus: DakStatus.CREATED,
      status: false,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          assignedOn: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a DakHistory', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
          assignedOn: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          assignedOn: currentDate,
          createdOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new DakHistory()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DakHistory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          assignedBy: 'BBBBBB',
          assignedOn: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          dakStatus: 'BBBBBB',
          status: true,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          assignedOn: currentDate,
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

    it('should partial update a DakHistory', () => {
      const patchObject = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          assignedOn: currentDate.format(DATE_TIME_FORMAT),
          dakStatus: 'BBBBBB',
          status: true,
        },
        new DakHistory()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
          assignedOn: currentDate,
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

    it('should return a list of DakHistory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          assignedBy: 'BBBBBB',
          assignedOn: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          dakStatus: 'BBBBBB',
          status: true,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          assignedOn: currentDate,
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

    it('should delete a DakHistory', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDakHistoryToCollectionIfMissing', () => {
      it('should add a DakHistory to an empty array', () => {
        const dakHistory: IDakHistory = { id: 123 };
        expectedResult = service.addDakHistoryToCollectionIfMissing([], dakHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dakHistory);
      });

      it('should not add a DakHistory to an array that contains it', () => {
        const dakHistory: IDakHistory = { id: 123 };
        const dakHistoryCollection: IDakHistory[] = [
          {
            ...dakHistory,
          },
          { id: 456 },
        ];
        expectedResult = service.addDakHistoryToCollectionIfMissing(dakHistoryCollection, dakHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DakHistory to an array that doesn't contain it", () => {
        const dakHistory: IDakHistory = { id: 123 };
        const dakHistoryCollection: IDakHistory[] = [{ id: 456 }];
        expectedResult = service.addDakHistoryToCollectionIfMissing(dakHistoryCollection, dakHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dakHistory);
      });

      it('should add only unique DakHistory to an array', () => {
        const dakHistoryArray: IDakHistory[] = [{ id: 123 }, { id: 456 }, { id: 3971 }];
        const dakHistoryCollection: IDakHistory[] = [{ id: 123 }];
        expectedResult = service.addDakHistoryToCollectionIfMissing(dakHistoryCollection, ...dakHistoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dakHistory: IDakHistory = { id: 123 };
        const dakHistory2: IDakHistory = { id: 456 };
        expectedResult = service.addDakHistoryToCollectionIfMissing([], dakHistory, dakHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dakHistory);
        expect(expectedResult).toContain(dakHistory2);
      });

      it('should accept null and undefined values', () => {
        const dakHistory: IDakHistory = { id: 123 };
        expectedResult = service.addDakHistoryToCollectionIfMissing([], null, dakHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dakHistory);
      });

      it('should return initial array if no DakHistory is added', () => {
        const dakHistoryCollection: IDakHistory[] = [{ id: 123 }];
        expectedResult = service.addDakHistoryToCollectionIfMissing(dakHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(dakHistoryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
