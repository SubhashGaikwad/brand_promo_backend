import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDakIdGenerator, DakIdGenerator } from '../dak-id-generator.model';

import { DakIdGeneratorService } from './dak-id-generator.service';

describe('DakIdGenerator Service', () => {
  let service: DakIdGeneratorService;
  let httpMock: HttpTestingController;
  let elemDefault: IDakIdGenerator;
  let expectedResult: IDakIdGenerator | IDakIdGenerator[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DakIdGeneratorService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nextValInward: 0,
      nextValOutward: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DakIdGenerator', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DakIdGenerator()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DakIdGenerator', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nextValInward: 1,
          nextValOutward: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DakIdGenerator', () => {
      const patchObject = Object.assign({}, new DakIdGenerator());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DakIdGenerator', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nextValInward: 1,
          nextValOutward: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DakIdGenerator', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDakIdGeneratorToCollectionIfMissing', () => {
      it('should add a DakIdGenerator to an empty array', () => {
        const dakIdGenerator: IDakIdGenerator = { id: 123 };
        expectedResult = service.addDakIdGeneratorToCollectionIfMissing([], dakIdGenerator);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dakIdGenerator);
      });

      it('should not add a DakIdGenerator to an array that contains it', () => {
        const dakIdGenerator: IDakIdGenerator = { id: 123 };
        const dakIdGeneratorCollection: IDakIdGenerator[] = [
          {
            ...dakIdGenerator,
          },
          { id: 456 },
        ];
        expectedResult = service.addDakIdGeneratorToCollectionIfMissing(dakIdGeneratorCollection, dakIdGenerator);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DakIdGenerator to an array that doesn't contain it", () => {
        const dakIdGenerator: IDakIdGenerator = { id: 123 };
        const dakIdGeneratorCollection: IDakIdGenerator[] = [{ id: 456 }];
        expectedResult = service.addDakIdGeneratorToCollectionIfMissing(dakIdGeneratorCollection, dakIdGenerator);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dakIdGenerator);
      });

      it('should add only unique DakIdGenerator to an array', () => {
        const dakIdGeneratorArray: IDakIdGenerator[] = [{ id: 123 }, { id: 456 }, { id: 95508 }];
        const dakIdGeneratorCollection: IDakIdGenerator[] = [{ id: 123 }];
        expectedResult = service.addDakIdGeneratorToCollectionIfMissing(dakIdGeneratorCollection, ...dakIdGeneratorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dakIdGenerator: IDakIdGenerator = { id: 123 };
        const dakIdGenerator2: IDakIdGenerator = { id: 456 };
        expectedResult = service.addDakIdGeneratorToCollectionIfMissing([], dakIdGenerator, dakIdGenerator2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dakIdGenerator);
        expect(expectedResult).toContain(dakIdGenerator2);
      });

      it('should accept null and undefined values', () => {
        const dakIdGenerator: IDakIdGenerator = { id: 123 };
        expectedResult = service.addDakIdGeneratorToCollectionIfMissing([], null, dakIdGenerator, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dakIdGenerator);
      });

      it('should return initial array if no DakIdGenerator is added', () => {
        const dakIdGeneratorCollection: IDakIdGenerator[] = [{ id: 123 }];
        expectedResult = service.addDakIdGeneratorToCollectionIfMissing(dakIdGeneratorCollection, undefined, null);
        expect(expectedResult).toEqual(dakIdGeneratorCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
