import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DakJourneyService } from '../service/dak-journey.service';
import { IDakJourney, DakJourney } from '../dak-journey.model';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { DakMasterService } from 'app/entities/dak-master/service/dak-master.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ICommentMaster } from 'app/entities/comment-master/comment-master.model';
import { CommentMasterService } from 'app/entities/comment-master/service/comment-master.service';

import { DakJourneyUpdateComponent } from './dak-journey-update.component';

describe('DakJourney Management Update Component', () => {
  let comp: DakJourneyUpdateComponent;
  let fixture: ComponentFixture<DakJourneyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dakJourneyService: DakJourneyService;
  let dakMasterService: DakMasterService;
  let securityUserService: SecurityUserService;
  let commentMasterService: CommentMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DakJourneyUpdateComponent],
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
      .overrideTemplate(DakJourneyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DakJourneyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dakJourneyService = TestBed.inject(DakJourneyService);
    dakMasterService = TestBed.inject(DakMasterService);
    securityUserService = TestBed.inject(SecurityUserService);
    commentMasterService = TestBed.inject(CommentMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DakMaster query and add missing value', () => {
      const dakJourney: IDakJourney = { id: 456 };
      const dakMaster: IDakMaster = { id: 86576 };
      dakJourney.dakMaster = dakMaster;

      const dakMasterCollection: IDakMaster[] = [{ id: 11761 }];
      jest.spyOn(dakMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: dakMasterCollection })));
      const additionalDakMasters = [dakMaster];
      const expectedCollection: IDakMaster[] = [...additionalDakMasters, ...dakMasterCollection];
      jest.spyOn(dakMasterService, 'addDakMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dakJourney });
      comp.ngOnInit();

      expect(dakMasterService.query).toHaveBeenCalled();
      expect(dakMasterService.addDakMasterToCollectionIfMissing).toHaveBeenCalledWith(dakMasterCollection, ...additionalDakMasters);
      expect(comp.dakMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityUser query and add missing value', () => {
      const dakJourney: IDakJourney = { id: 456 };
      const securityUser: ISecurityUser = { id: 16216 };
      dakJourney.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 66778 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dakJourney });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CommentMaster query and add missing value', () => {
      const dakJourney: IDakJourney = { id: 456 };
      const commentMaster: ICommentMaster = { id: 72931 };
      dakJourney.commentMaster = commentMaster;

      const commentMasterCollection: ICommentMaster[] = [{ id: 13534 }];
      jest.spyOn(commentMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: commentMasterCollection })));
      const additionalCommentMasters = [commentMaster];
      const expectedCollection: ICommentMaster[] = [...additionalCommentMasters, ...commentMasterCollection];
      jest.spyOn(commentMasterService, 'addCommentMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dakJourney });
      comp.ngOnInit();

      expect(commentMasterService.query).toHaveBeenCalled();
      expect(commentMasterService.addCommentMasterToCollectionIfMissing).toHaveBeenCalledWith(
        commentMasterCollection,
        ...additionalCommentMasters
      );
      expect(comp.commentMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dakJourney: IDakJourney = { id: 456 };
      const dakMaster: IDakMaster = { id: 75379 };
      dakJourney.dakMaster = dakMaster;
      const securityUser: ISecurityUser = { id: 38816 };
      dakJourney.securityUser = securityUser;
      const commentMaster: ICommentMaster = { id: 13647 };
      dakJourney.commentMaster = commentMaster;

      activatedRoute.data = of({ dakJourney });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dakJourney));
      expect(comp.dakMastersSharedCollection).toContain(dakMaster);
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.commentMastersSharedCollection).toContain(commentMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DakJourney>>();
      const dakJourney = { id: 123 };
      jest.spyOn(dakJourneyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dakJourney });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dakJourney }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dakJourneyService.update).toHaveBeenCalledWith(dakJourney);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DakJourney>>();
      const dakJourney = new DakJourney();
      jest.spyOn(dakJourneyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dakJourney });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dakJourney }));
      saveSubject.complete();

      // THEN
      expect(dakJourneyService.create).toHaveBeenCalledWith(dakJourney);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DakJourney>>();
      const dakJourney = { id: 123 };
      jest.spyOn(dakJourneyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dakJourney });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dakJourneyService.update).toHaveBeenCalledWith(dakJourney);
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

    describe('trackCommentMasterById', () => {
      it('Should return tracked CommentMaster primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCommentMasterById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
