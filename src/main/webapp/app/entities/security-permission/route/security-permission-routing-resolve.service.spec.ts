import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISecurityPermission, SecurityPermission } from '../security-permission.model';
import { SecurityPermissionService } from '../service/security-permission.service';

import { SecurityPermissionRoutingResolveService } from './security-permission-routing-resolve.service';

describe('SecurityPermission routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SecurityPermissionRoutingResolveService;
  let service: SecurityPermissionService;
  let resultSecurityPermission: ISecurityPermission | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(SecurityPermissionRoutingResolveService);
    service = TestBed.inject(SecurityPermissionService);
    resultSecurityPermission = undefined;
  });

  describe('resolve', () => {
    it('should return ISecurityPermission returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSecurityPermission = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSecurityPermission).toEqual({ id: 123 });
    });

    it('should return new ISecurityPermission if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSecurityPermission = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSecurityPermission).toEqual(new SecurityPermission());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SecurityPermission })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSecurityPermission = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSecurityPermission).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
