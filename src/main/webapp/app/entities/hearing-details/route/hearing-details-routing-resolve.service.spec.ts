import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IHearingDetails, HearingDetails } from '../hearing-details.model';
import { HearingDetailsService } from '../service/hearing-details.service';

import { HearingDetailsRoutingResolveService } from './hearing-details-routing-resolve.service';

describe('HearingDetails routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: HearingDetailsRoutingResolveService;
  let service: HearingDetailsService;
  let resultHearingDetails: IHearingDetails | undefined;

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
    routingResolveService = TestBed.inject(HearingDetailsRoutingResolveService);
    service = TestBed.inject(HearingDetailsService);
    resultHearingDetails = undefined;
  });

  describe('resolve', () => {
    it('should return IHearingDetails returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHearingDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHearingDetails).toEqual({ id: 123 });
    });

    it('should return new IHearingDetails if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHearingDetails = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultHearingDetails).toEqual(new HearingDetails());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as HearingDetails })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHearingDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHearingDetails).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
