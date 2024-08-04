import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IGhoshwara, Ghoshwara } from '../ghoshwara.model';
import { GhoshwaraService } from '../service/ghoshwara.service';

import { GhoshwaraRoutingResolveService } from './ghoshwara-routing-resolve.service';

describe('Ghoshwara routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: GhoshwaraRoutingResolveService;
  let service: GhoshwaraService;
  let resultGhoshwara: IGhoshwara | undefined;

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
    routingResolveService = TestBed.inject(GhoshwaraRoutingResolveService);
    service = TestBed.inject(GhoshwaraService);
    resultGhoshwara = undefined;
  });

  describe('resolve', () => {
    it('should return IGhoshwara returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGhoshwara = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultGhoshwara).toEqual({ id: 123 });
    });

    it('should return new IGhoshwara if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGhoshwara = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultGhoshwara).toEqual(new Ghoshwara());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Ghoshwara })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGhoshwara = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultGhoshwara).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
