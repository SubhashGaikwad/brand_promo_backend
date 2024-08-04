import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDakIdGenerator, DakIdGenerator } from '../dak-id-generator.model';
import { DakIdGeneratorService } from '../service/dak-id-generator.service';

import { DakIdGeneratorRoutingResolveService } from './dak-id-generator-routing-resolve.service';

describe('DakIdGenerator routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DakIdGeneratorRoutingResolveService;
  let service: DakIdGeneratorService;
  let resultDakIdGenerator: IDakIdGenerator | undefined;

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
    routingResolveService = TestBed.inject(DakIdGeneratorRoutingResolveService);
    service = TestBed.inject(DakIdGeneratorService);
    resultDakIdGenerator = undefined;
  });

  describe('resolve', () => {
    it('should return IDakIdGenerator returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDakIdGenerator = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDakIdGenerator).toEqual({ id: 123 });
    });

    it('should return new IDakIdGenerator if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDakIdGenerator = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDakIdGenerator).toEqual(new DakIdGenerator());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DakIdGenerator })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDakIdGenerator = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDakIdGenerator).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
