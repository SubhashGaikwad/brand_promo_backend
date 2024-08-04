import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DakHistoryService } from '../service/dak-history.service';
import { IDakHistory, DakHistory } from '../dak-history.model';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { DakMasterService } from 'app/entities/dak-master/service/dak-master.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

import { DakHistoryUpdateComponent } from './dak-history-update.component';

describe('DakHistory Management Update Component', () => {
  let comp: DakHistoryUpdateComponent;
  let fixture: ComponentFixture<DakHistoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dakHistoryService: DakHistoryService;
  let dakMasterService: DakMasterService;
  let securityUserService: SecurityUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DakHistoryUpdateComponent],
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
      .overrideTemplate(DakHistoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DakHistoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dakHistoryService = TestBed.inject(DakHistoryService);
    dakMasterService = TestBed.inject(DakMasterService);
    securityUserService = TestBed.inject(SecurityUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DakMaster query and add missing value', () => {
      const dakHistory: IDakHistory = { id: 456 };
      const dakMaster: IDakMaster = { id: 18829 };
      dakHistory.dakMaster = dakMaster;

      const dakMasterCollection: IDakMaster[] = [{ id: 13035 }];
      jest.spyOn(dakMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: dakMasterCollection })));
      const additionalDakMasters = [dakMaster];
      const expectedCollection: IDakMaster[] = [...additionalDakMasters, ...dakMasterCollection];
      jest.spyOn(dakMasterService, 'addDakMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dakHistory });
      comp.ngOnInit();

      expect(dakMasterService.query).toHaveBeenCalled();
      expect(dakMasterService.addDakMasterToCollectionIfMissing).toHaveBeenCalledWith(dakMasterCollection, ...additionalDakMasters);
      expect(comp.dakMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityUser query and add missing value', () => {
      const dakHistory: IDakHistory = { id: 456 };
      const securityUser: ISecurityUser = { id: 84563 };
      dakHistory.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 12237 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dakHistory });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dakHistory: IDakHistory = { id: 456 };
      const dakMaster: IDakMaster = { id: 63074 };
      dakHistory.dakMaster = dakMaster;
      const securityUser: ISecurityUser = { id: 99029 };
      dakHistory.securityUser = securityUser;

      activatedRoute.data = of({ dakHistory });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dakHistory));
      expect(comp.dakMastersSharedCollection).toContain(dakMaster);
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DakHistory>>();
      const dakHistory = { id: 123 };
      jest.spyOn(dakHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dakHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dakHistory }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dakHistoryService.update).toHaveBeenCalledWith(dakHistory);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DakHistory>>();
      const dakHistory = new DakHistory();
      jest.spyOn(dakHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dakHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dakHistory }));
      saveSubject.complete();

      // THEN
      expect(dakHistoryService.create).toHaveBeenCalledWith(dakHistory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DakHistory>>();
      const dakHistory = { id: 123 };
      jest.spyOn(dakHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dakHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dakHistoryService.update).toHaveBeenCalledWith(dakHistory);
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

    describe('trackSecurityUserById', () => {
      it('Should return tracked SecurityUser primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
