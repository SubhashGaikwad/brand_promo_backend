import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { RegisterType } from 'app/entities/enumerations/register-type.model';
import { IGhoshwara, Ghoshwara } from '../ghoshwara.model';

import { GhoshwaraService } from './ghoshwara.service';

describe('Ghoshwara Service', () => {
  let service: GhoshwaraService;
  let httpMock: HttpTestingController;
  let elemDefault: IGhoshwara;
  let expectedResult: IGhoshwara | IGhoshwara[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GhoshwaraService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      registerType: RegisterType.WORK_DESCRIPTION,
      initialPendings: 0,
      currentWeekInwards: 0,
      total: 0,
      selfGenerated: 0,
      currentWeekCleared: 0,
      weeklyPendings: 0,
      firstWeek: 0,
      secondWeek: 0,
      thirdWeek: 0,
      firstMonth: 0,
      secondMonth: 0,
      thirdMonth: 0,
      withinSixMonths: 0,
      date: currentDate,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Ghoshwara', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new Ghoshwara()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Ghoshwara', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          registerType: 'BBBBBB',
          initialPendings: 1,
          currentWeekInwards: 1,
          total: 1,
          selfGenerated: 1,
          currentWeekCleared: 1,
          weeklyPendings: 1,
          firstWeek: 1,
          secondWeek: 1,
          thirdWeek: 1,
          firstMonth: 1,
          secondMonth: 1,
          thirdMonth: 1,
          withinSixMonths: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Ghoshwara', () => {
      const patchObject = Object.assign(
        {
          total: 1,
          selfGenerated: 1,
          currentWeekCleared: 1,
          firstWeek: 1,
          secondWeek: 1,
          thirdWeek: 1,
          firstMonth: 1,
          lastModifiedBy: 'BBBBBB',
        },
        new Ghoshwara()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Ghoshwara', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          registerType: 'BBBBBB',
          initialPendings: 1,
          currentWeekInwards: 1,
          total: 1,
          selfGenerated: 1,
          currentWeekCleared: 1,
          weeklyPendings: 1,
          firstWeek: 1,
          secondWeek: 1,
          thirdWeek: 1,
          firstMonth: 1,
          secondMonth: 1,
          thirdMonth: 1,
          withinSixMonths: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
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

    it('should delete a Ghoshwara', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addGhoshwaraToCollectionIfMissing', () => {
      it('should add a Ghoshwara to an empty array', () => {
        const ghoshwara: IGhoshwara = { id: 123 };
        expectedResult = service.addGhoshwaraToCollectionIfMissing([], ghoshwara);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ghoshwara);
      });

      it('should not add a Ghoshwara to an array that contains it', () => {
        const ghoshwara: IGhoshwara = { id: 123 };
        const ghoshwaraCollection: IGhoshwara[] = [
          {
            ...ghoshwara,
          },
          { id: 456 },
        ];
        expectedResult = service.addGhoshwaraToCollectionIfMissing(ghoshwaraCollection, ghoshwara);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Ghoshwara to an array that doesn't contain it", () => {
        const ghoshwara: IGhoshwara = { id: 123 };
        const ghoshwaraCollection: IGhoshwara[] = [{ id: 456 }];
        expectedResult = service.addGhoshwaraToCollectionIfMissing(ghoshwaraCollection, ghoshwara);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ghoshwara);
      });

      it('should add only unique Ghoshwara to an array', () => {
        const ghoshwaraArray: IGhoshwara[] = [{ id: 123 }, { id: 456 }, { id: 45432 }];
        const ghoshwaraCollection: IGhoshwara[] = [{ id: 123 }];
        expectedResult = service.addGhoshwaraToCollectionIfMissing(ghoshwaraCollection, ...ghoshwaraArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ghoshwara: IGhoshwara = { id: 123 };
        const ghoshwara2: IGhoshwara = { id: 456 };
        expectedResult = service.addGhoshwaraToCollectionIfMissing([], ghoshwara, ghoshwara2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ghoshwara);
        expect(expectedResult).toContain(ghoshwara2);
      });

      it('should accept null and undefined values', () => {
        const ghoshwara: IGhoshwara = { id: 123 };
        expectedResult = service.addGhoshwaraToCollectionIfMissing([], null, ghoshwara, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ghoshwara);
      });

      it('should return initial array if no Ghoshwara is added', () => {
        const ghoshwaraCollection: IGhoshwara[] = [{ id: 123 }];
        expectedResult = service.addGhoshwaraToCollectionIfMissing(ghoshwaraCollection, undefined, null);
        expect(expectedResult).toEqual(ghoshwaraCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
