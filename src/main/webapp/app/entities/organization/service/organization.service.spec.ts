import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { OrganizationType } from 'app/entities/enumerations/organization-type.model';
import { IOrganization, Organization } from '../organization.model';

import { OrganizationService } from './organization.service';

describe('Organization Service', () => {
  let service: OrganizationService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrganization;
  let expectedResult: IOrganization | IOrganization[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      organizationName: 'AAAAAAA',
      address: 'AAAAAAA',
      createdOn: 'AAAAAAA',
      description: 'AAAAAAA',
      isActivate: false,
      orgnizationType: OrganizationType.AR,
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

    it('should create a Organization', () => {
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

      service.create(new Organization()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Organization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          organizationName: 'BBBBBB',
          address: 'BBBBBB',
          createdOn: 'BBBBBB',
          description: 'BBBBBB',
          isActivate: true,
          orgnizationType: 'BBBBBB',
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

    it('should partial update a Organization', () => {
      const patchObject = Object.assign(
        {
          organizationName: 'BBBBBB',
          address: 'BBBBBB',
          description: 'BBBBBB',
          isActivate: true,
          orgnizationType: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new Organization()
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

    it('should return a list of Organization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          organizationName: 'BBBBBB',
          address: 'BBBBBB',
          createdOn: 'BBBBBB',
          description: 'BBBBBB',
          isActivate: true,
          orgnizationType: 'BBBBBB',
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

    it('should delete a Organization', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrganizationToCollectionIfMissing', () => {
      it('should add a Organization to an empty array', () => {
        const organization: IOrganization = { id: 123 };
        expectedResult = service.addOrganizationToCollectionIfMissing([], organization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organization);
      });

      it('should not add a Organization to an array that contains it', () => {
        const organization: IOrganization = { id: 123 };
        const organizationCollection: IOrganization[] = [
          {
            ...organization,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrganizationToCollectionIfMissing(organizationCollection, organization);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Organization to an array that doesn't contain it", () => {
        const organization: IOrganization = { id: 123 };
        const organizationCollection: IOrganization[] = [{ id: 456 }];
        expectedResult = service.addOrganizationToCollectionIfMissing(organizationCollection, organization);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organization);
      });

      it('should add only unique Organization to an array', () => {
        const organizationArray: IOrganization[] = [{ id: 123 }, { id: 456 }, { id: 24012 }];
        const organizationCollection: IOrganization[] = [{ id: 123 }];
        expectedResult = service.addOrganizationToCollectionIfMissing(organizationCollection, ...organizationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organization: IOrganization = { id: 123 };
        const organization2: IOrganization = { id: 456 };
        expectedResult = service.addOrganizationToCollectionIfMissing([], organization, organization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organization);
        expect(expectedResult).toContain(organization2);
      });

      it('should accept null and undefined values', () => {
        const organization: IOrganization = { id: 123 };
        expectedResult = service.addOrganizationToCollectionIfMissing([], null, organization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organization);
      });

      it('should return initial array if no Organization is added', () => {
        const organizationCollection: IOrganization[] = [{ id: 123 }];
        expectedResult = service.addOrganizationToCollectionIfMissing(organizationCollection, undefined, null);
        expect(expectedResult).toEqual(organizationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
