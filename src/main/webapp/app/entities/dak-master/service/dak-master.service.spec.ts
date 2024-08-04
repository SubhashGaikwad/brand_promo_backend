import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';
import { LetterType } from 'app/entities/enumerations/letter-type.model';
import { IDakMaster, DakMaster } from '../dak-master.model';

import { DakMasterService } from './dak-master.service';

describe('DakMaster Service', () => {
  let service: DakMasterService;
  let httpMock: HttpTestingController;
  let elemDefault: IDakMaster;
  let expectedResult: IDakMaster | IDakMaster[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DakMasterService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      inwardNumber: 'AAAAAAA',
      senderName: 'AAAAAAA',
      contactNumber: 'AAAAAAA',
      senderAddress: 'AAAAAAA',
      senderEmail: 'AAAAAAA',
      subject: 'AAAAAAA',
      letterDate: currentDate,
      currentStatus: DakStatus.CREATED,
      letterStatus: false,
      letterReceivedDate: currentDate,
      awaitReason: 'AAAAAAA',
      dispatchDate: currentDate,
      createdBy: 'AAAAAAA',
      letterType: LetterType.INWARD,
      isResponseReceived: false,
      assignedDate: currentDate,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
      dakAssignedFrom: 'AAAAAAA',
      dakAssignee: 'AAAAAAA',
      dispatchBy: 'AAAAAAA',
      senderOutward: 'AAAAAAA',
      outwardNumber: 'AAAAAAA',
      taluka: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          letterDate: currentDate.format(DATE_TIME_FORMAT),
          letterReceivedDate: currentDate.format(DATE_TIME_FORMAT),
          dispatchDate: currentDate.format(DATE_TIME_FORMAT),
          assignedDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DakMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          letterDate: currentDate.format(DATE_TIME_FORMAT),
          letterReceivedDate: currentDate.format(DATE_TIME_FORMAT),
          dispatchDate: currentDate.format(DATE_TIME_FORMAT),
          assignedDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          letterDate: currentDate,
          letterReceivedDate: currentDate,
          dispatchDate: currentDate,
          assignedDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new DakMaster()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DakMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          inwardNumber: 'BBBBBB',
          senderName: 'BBBBBB',
          contactNumber: 'BBBBBB',
          senderAddress: 'BBBBBB',
          senderEmail: 'BBBBBB',
          subject: 'BBBBBB',
          letterDate: currentDate.format(DATE_TIME_FORMAT),
          currentStatus: 'BBBBBB',
          letterStatus: true,
          letterReceivedDate: currentDate.format(DATE_TIME_FORMAT),
          awaitReason: 'BBBBBB',
          dispatchDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 'BBBBBB',
          letterType: 'BBBBBB',
          isResponseReceived: true,
          assignedDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          dakAssignedFrom: 'BBBBBB',
          dakAssignee: 'BBBBBB',
          dispatchBy: 'BBBBBB',
          senderOutward: 'BBBBBB',
          outwardNumber: 'BBBBBB',
          taluka: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          letterDate: currentDate,
          letterReceivedDate: currentDate,
          dispatchDate: currentDate,
          assignedDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DakMaster', () => {
      const patchObject = Object.assign(
        {
          inwardNumber: 'BBBBBB',
          senderName: 'BBBBBB',
          senderAddress: 'BBBBBB',
          subject: 'BBBBBB',
          letterDate: currentDate.format(DATE_TIME_FORMAT),
          currentStatus: 'BBBBBB',
          letterReceivedDate: currentDate.format(DATE_TIME_FORMAT),
          awaitReason: 'BBBBBB',
          letterType: 'BBBBBB',
          isResponseReceived: true,
          assignedDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          dakAssignedFrom: 'BBBBBB',
          dispatchBy: 'BBBBBB',
          outwardNumber: 'BBBBBB',
          taluka: 'BBBBBB',
        },
        new DakMaster()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          letterDate: currentDate,
          letterReceivedDate: currentDate,
          dispatchDate: currentDate,
          assignedDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DakMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          inwardNumber: 'BBBBBB',
          senderName: 'BBBBBB',
          contactNumber: 'BBBBBB',
          senderAddress: 'BBBBBB',
          senderEmail: 'BBBBBB',
          subject: 'BBBBBB',
          letterDate: currentDate.format(DATE_TIME_FORMAT),
          currentStatus: 'BBBBBB',
          letterStatus: true,
          letterReceivedDate: currentDate.format(DATE_TIME_FORMAT),
          awaitReason: 'BBBBBB',
          dispatchDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 'BBBBBB',
          letterType: 'BBBBBB',
          isResponseReceived: true,
          assignedDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          dakAssignedFrom: 'BBBBBB',
          dakAssignee: 'BBBBBB',
          dispatchBy: 'BBBBBB',
          senderOutward: 'BBBBBB',
          outwardNumber: 'BBBBBB',
          taluka: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          letterDate: currentDate,
          letterReceivedDate: currentDate,
          dispatchDate: currentDate,
          assignedDate: currentDate,
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

    it('should delete a DakMaster', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDakMasterToCollectionIfMissing', () => {
      it('should add a DakMaster to an empty array', () => {
        const dakMaster: IDakMaster = { id: 123 };
        expectedResult = service.addDakMasterToCollectionIfMissing([], dakMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dakMaster);
      });

      it('should not add a DakMaster to an array that contains it', () => {
        const dakMaster: IDakMaster = { id: 123 };
        const dakMasterCollection: IDakMaster[] = [
          {
            ...dakMaster,
          },
          { id: 456 },
        ];
        expectedResult = service.addDakMasterToCollectionIfMissing(dakMasterCollection, dakMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DakMaster to an array that doesn't contain it", () => {
        const dakMaster: IDakMaster = { id: 123 };
        const dakMasterCollection: IDakMaster[] = [{ id: 456 }];
        expectedResult = service.addDakMasterToCollectionIfMissing(dakMasterCollection, dakMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dakMaster);
      });

      it('should add only unique DakMaster to an array', () => {
        const dakMasterArray: IDakMaster[] = [{ id: 123 }, { id: 456 }, { id: 3027 }];
        const dakMasterCollection: IDakMaster[] = [{ id: 123 }];
        expectedResult = service.addDakMasterToCollectionIfMissing(dakMasterCollection, ...dakMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dakMaster: IDakMaster = { id: 123 };
        const dakMaster2: IDakMaster = { id: 456 };
        expectedResult = service.addDakMasterToCollectionIfMissing([], dakMaster, dakMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dakMaster);
        expect(expectedResult).toContain(dakMaster2);
      });

      it('should accept null and undefined values', () => {
        const dakMaster: IDakMaster = { id: 123 };
        expectedResult = service.addDakMasterToCollectionIfMissing([], null, dakMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dakMaster);
      });

      it('should return initial array if no DakMaster is added', () => {
        const dakMasterCollection: IDakMaster[] = [{ id: 123 }];
        expectedResult = service.addDakMasterToCollectionIfMissing(dakMasterCollection, undefined, null);
        expect(expectedResult).toEqual(dakMasterCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
