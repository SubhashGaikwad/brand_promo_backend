import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HearingDetailsService } from '../service/hearing-details.service';
import { IHearingDetails, HearingDetails } from '../hearing-details.model';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { DakMasterService } from 'app/entities/dak-master/service/dak-master.service';

import { HearingDetailsUpdateComponent } from './hearing-details-update.component';

describe('HearingDetails Management Update Component', () => {
  let comp: HearingDetailsUpdateComponent;
  let fixture: ComponentFixture<HearingDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hearingDetailsService: HearingDetailsService;
  let dakMasterService: DakMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HearingDetailsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(HearingDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HearingDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hearingDetailsService = TestBed.inject(HearingDetailsService);
    dakMasterService = TestBed.inject(DakMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DakMaster query and add missing value', () => {
      const hearingDetails: IHearingDetails = { id: 456 };
      const dakMaster: IDakMaster = { id: 69241 };
      hearingDetails.dakMaster = dakMaster;

      const dakMasterCollection: IDakMaster[] = [{ id: 77214 }];
      jest.spyOn(dakMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: dakMasterCollection })));
      const additionalDakMasters = [dakMaster];
      const expectedCollection: IDakMaster[] = [...additionalDakMasters, ...dakMasterCollection];
      jest.spyOn(dakMasterService, 'addDakMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hearingDetails });
      comp.ngOnInit();

      expect(dakMasterService.query).toHaveBeenCalled();
      expect(dakMasterService.addDakMasterToCollectionIfMissing).toHaveBeenCalledWith(dakMasterCollection, ...additionalDakMasters);
      expect(comp.dakMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hearingDetails: IHearingDetails = { id: 456 };
      const dakMaster: IDakMaster = { id: 23652 };
      hearingDetails.dakMaster = dakMaster;

      activatedRoute.data = of({ hearingDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(hearingDetails));
      expect(comp.dakMastersSharedCollection).toContain(dakMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HearingDetails>>();
      const hearingDetails = { id: 123 };
      jest.spyOn(hearingDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hearingDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hearingDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(hearingDetailsService.update).toHaveBeenCalledWith(hearingDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HearingDetails>>();
      const hearingDetails = new HearingDetails();
      jest.spyOn(hearingDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hearingDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hearingDetails }));
      saveSubject.complete();

      // THEN
      expect(hearingDetailsService.create).toHaveBeenCalledWith(hearingDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HearingDetails>>();
      const hearingDetails = { id: 123 };
      jest.spyOn(hearingDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hearingDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hearingDetailsService.update).toHaveBeenCalledWith(hearingDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDakMasterById', () => {
      it('Should return tracked DakMaster primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDakMasterById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
