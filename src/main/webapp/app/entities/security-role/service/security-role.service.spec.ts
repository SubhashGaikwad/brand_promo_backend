import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISecurityRole, SecurityRole } from '../security-role.model';

import { SecurityRoleService } from './security-role.service';

describe('SecurityRole Service', () => {
  let service: SecurityRoleService;
  let httpMock: HttpTestingController;
  let elemDefault: ISecurityRole;
  let expectedResult: ISecurityRole | ISecurityRole[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SecurityRoleService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      roleName: 'AAAAAAA',
      description: 'AAAAAAA',
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

    it('should create a SecurityRole', () => {
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

      service.create(new SecurityRole()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SecurityRole', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          roleName: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should partial update a SecurityRole', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new SecurityRole()
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

    it('should return a list of SecurityRole', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          roleName: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should delete a SecurityRole', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSecurityRoleToCollectionIfMissing', () => {
      it('should add a SecurityRole to an empty array', () => {
        const securityRole: ISecurityRole = { id: 123 };
        expectedResult = service.addSecurityRoleToCollectionIfMissing([], securityRole);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityRole);
      });

      it('should not add a SecurityRole to an array that contains it', () => {
        const securityRole: ISecurityRole = { id: 123 };
        const securityRoleCollection: ISecurityRole[] = [
          {
            ...securityRole,
          },
          { id: 456 },
        ];
        expectedResult = service.addSecurityRoleToCollectionIfMissing(securityRoleCollection, securityRole);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SecurityRole to an array that doesn't contain it", () => {
        const securityRole: ISecurityRole = { id: 123 };
        const securityRoleCollection: ISecurityRole[] = [{ id: 456 }];
        expectedResult = service.addSecurityRoleToCollectionIfMissing(securityRoleCollection, securityRole);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityRole);
      });

      it('should add only unique SecurityRole to an array', () => {
        const securityRoleArray: ISecurityRole[] = [{ id: 123 }, { id: 456 }, { id: 50871 }];
        const securityRoleCollection: ISecurityRole[] = [{ id: 123 }];
        expectedResult = service.addSecurityRoleToCollectionIfMissing(securityRoleCollection, ...securityRoleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const securityRole: ISecurityRole = { id: 123 };
        const securityRole2: ISecurityRole = { id: 456 };
        expectedResult = service.addSecurityRoleToCollectionIfMissing([], securityRole, securityRole2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityRole);
        expect(expectedResult).toContain(securityRole2);
      });

      it('should accept null and undefined values', () => {
        const securityRole: ISecurityRole = { id: 123 };
        expectedResult = service.addSecurityRoleToCollectionIfMissing([], null, securityRole, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityRole);
      });

      it('should return initial array if no SecurityRole is added', () => {
        const securityRoleCollection: ISecurityRole[] = [{ id: 123 }];
        expectedResult = service.addSecurityRoleToCollectionIfMissing(securityRoleCollection, undefined, null);
        expect(expectedResult).toEqual(securityRoleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
