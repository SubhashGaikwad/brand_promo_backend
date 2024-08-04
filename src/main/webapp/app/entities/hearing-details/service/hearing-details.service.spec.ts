import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';
import { IHearingDetails, HearingDetails } from '../hearing-details.model';

import { HearingDetailsService } from './hearing-details.service';

describe('HearingDetails Service', () => {
  let service: HearingDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IHearingDetails;
  let expectedResult: IHearingDetails | IHearingDetails[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HearingDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      accuserName: 'AAAAAAA',
      orderDate: currentDate,
      respondentName: 'AAAAAAA',
      comment: 'AAAAAAA',
      date: currentDate,
      time: currentDate,
      status: DakStatus.CREATED,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          orderDate: currentDate.format(DATE_TIME_FORMAT),
          date: currentDate.format(DATE_TIME_FORMAT),
          time: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a HearingDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          orderDate: currentDate.format(DATE_TIME_FORMAT),
          date: currentDate.format(DATE_TIME_FORMAT),
          time: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          orderDate: currentDate,
          date: currentDate,
          time: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new HearingDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HearingDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          accuserName: 'BBBBBB',
          orderDate: currentDate.format(DATE_TIME_FORMAT),
          respondentName: 'BBBBBB',
          comment: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          time: currentDate.format(DATE_TIME_FORMAT),
          status: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          orderDate: currentDate,
          date: currentDate,
          time: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HearingDetails', () => {
      const patchObject = Object.assign(
        {
          accuserName: 'BBBBBB',
          orderDate: currentDate.format(DATE_TIME_FORMAT),
          respondentName: 'BBBBBB',
          status: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new HearingDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          orderDate: currentDate,
          date: currentDate,
          time: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HearingDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          accuserName: 'BBBBBB',
          orderDate: currentDate.format(DATE_TIME_FORMAT),
          respondentName: 'BBBBBB',
          comment: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          time: currentDate.format(DATE_TIME_FORMAT),
          status: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          orderDate: currentDate,
          date: currentDate,
          time: currentDate,
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

    it('should delete a HearingDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHearingDetailsToCollectionIfMissing', () => {
      it('should add a HearingDetails to an empty array', () => {
        const hearingDetails: IHearingDetails = { id: 123 };
        expectedResult = service.addHearingDetailsToCollectionIfMissing([], hearingDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hearingDetails);
      });

      it('should not add a HearingDetails to an array that contains it', () => {
        const hearingDetails: IHearingDetails = { id: 123 };
        const hearingDetailsCollection: IHearingDetails[] = [
          {
            ...hearingDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addHearingDetailsToCollectionIfMissing(hearingDetailsCollection, hearingDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HearingDetails to an array that doesn't contain it", () => {
        const hearingDetails: IHearingDetails = { id: 123 };
        const hearingDetailsCollection: IHearingDetails[] = [{ id: 456 }];
        expectedResult = service.addHearingDetailsToCollectionIfMissing(hearingDetailsCollection, hearingDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hearingDetails);
      });

      it('should add only unique HearingDetails to an array', () => {
        const hearingDetailsArray: IHearingDetails[] = [{ id: 123 }, { id: 456 }, { id: 89563 }];
        const hearingDetailsCollection: IHearingDetails[] = [{ id: 123 }];
        expectedResult = service.addHearingDetailsToCollectionIfMissing(hearingDetailsCollection, ...hearingDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hearingDetails: IHearingDetails = { id: 123 };
        const hearingDetails2: IHearingDetails = { id: 456 };
        expectedResult = service.addHearingDetailsToCollectionIfMissing([], hearingDetails, hearingDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hearingDetails);
        expect(expectedResult).toContain(hearingDetails2);
      });

      it('should accept null and undefined values', () => {
        const hearingDetails: IHearingDetails = { id: 123 };
        expectedResult = service.addHearingDetailsToCollectionIfMissing([], null, hearingDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hearingDetails);
      });

      it('should return initial array if no HearingDetails is added', () => {
        const hearingDetailsCollection: IHearingDetails[] = [{ id: 123 }];
        expectedResult = service.addHearingDetailsToCollectionIfMissing(hearingDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(hearingDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
