import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';
import { IDakJourney, DakJourney } from '../dak-journey.model';

import { DakJourneyService } from './dak-journey.service';

describe('DakJourney Service', () => {
  let service: DakJourneyService;
  let httpMock: HttpTestingController;
  let elemDefault: IDakJourney;
  let expectedResult: IDakJourney | IDakJourney[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DakJourneyService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      assignedOn: currentDate,
      updatedOn: currentDate,
      dakStatus: DakStatus.CREATED,
      status: false,
      dakAssignedBy: 'AAAAAAA',
      dakAssignedTo: 'AAAAAAA',
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          assignedOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DakJourney', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          assignedOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          assignedOn: currentDate,
          updatedOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new DakJourney()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DakJourney', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          assignedOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
          dakStatus: 'BBBBBB',
          status: true,
          dakAssignedBy: 'BBBBBB',
          dakAssignedTo: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          assignedOn: currentDate,
          updatedOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DakJourney', () => {
      const patchObject = Object.assign(
        {
          status: true,
          dakAssignedTo: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new DakJourney()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          assignedOn: currentDate,
          updatedOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DakJourney', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          assignedOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
          dakStatus: 'BBBBBB',
          status: true,
          dakAssignedBy: 'BBBBBB',
          dakAssignedTo: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          assignedOn: currentDate,
          updatedOn: currentDate,
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

    it('should delete a DakJourney', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDakJourneyToCollectionIfMissing', () => {
      it('should add a DakJourney to an empty array', () => {
        const dakJourney: IDakJourney = { id: 123 };
        expectedResult = service.addDakJourneyToCollectionIfMissing([], dakJourney);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dakJourney);
      });

      it('should not add a DakJourney to an array that contains it', () => {
        const dakJourney: IDakJourney = { id: 123 };
        const dakJourneyCollection: IDakJourney[] = [
          {
            ...dakJourney,
          },
          { id: 456 },
        ];
        expectedResult = service.addDakJourneyToCollectionIfMissing(dakJourneyCollection, dakJourney);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DakJourney to an array that doesn't contain it", () => {
        const dakJourney: IDakJourney = { id: 123 };
        const dakJourneyCollection: IDakJourney[] = [{ id: 456 }];
        expectedResult = service.addDakJourneyToCollectionIfMissing(dakJourneyCollection, dakJourney);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dakJourney);
      });

      it('should add only unique DakJourney to an array', () => {
        const dakJourneyArray: IDakJourney[] = [{ id: 123 }, { id: 456 }, { id: 60857 }];
        const dakJourneyCollection: IDakJourney[] = [{ id: 123 }];
        expectedResult = service.addDakJourneyToCollectionIfMissing(dakJourneyCollection, ...dakJourneyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dakJourney: IDakJourney = { id: 123 };
        const dakJourney2: IDakJourney = { id: 456 };
        expectedResult = service.addDakJourneyToCollectionIfMissing([], dakJourney, dakJourney2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dakJourney);
        expect(expectedResult).toContain(dakJourney2);
      });

      it('should accept null and undefined values', () => {
        const dakJourney: IDakJourney = { id: 123 };
        expectedResult = service.addDakJourneyToCollectionIfMissing([], null, dakJourney, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dakJourney);
      });

      it('should return initial array if no DakJourney is added', () => {
        const dakJourneyCollection: IDakJourney[] = [{ id: 123 }];
        expectedResult = service.addDakJourneyToCollectionIfMissing(dakJourneyCollection, undefined, null);
        expect(expectedResult).toEqual(dakJourneyCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
