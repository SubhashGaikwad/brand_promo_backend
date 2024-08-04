import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommentMasterService } from '../service/comment-master.service';
import { ICommentMaster, CommentMaster } from '../comment-master.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { DakMasterService } from 'app/entities/dak-master/service/dak-master.service';

import { CommentMasterUpdateComponent } from './comment-master-update.component';

describe('CommentMaster Management Update Component', () => {
  let comp: CommentMasterUpdateComponent;
  let fixture: ComponentFixture<CommentMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commentMasterService: CommentMasterService;
  let securityUserService: SecurityUserService;
  let dakMasterService: DakMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommentMasterUpdateComponent],
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
      .overrideTemplate(CommentMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommentMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commentMasterService = TestBed.inject(CommentMasterService);
    securityUserService = TestBed.inject(SecurityUserService);
    dakMasterService = TestBed.inject(DakMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityUser query and add missing value', () => {
      const commentMaster: ICommentMaster = { id: 456 };
      const securityUser: ISecurityUser = { id: 27636 };
      commentMaster.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 57006 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commentMaster });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DakMaster query and add missing value', () => {
      const commentMaster: ICommentMaster = { id: 456 };
      const dakMaster: IDakMaster = { id: 41643 };
      commentMaster.dakMaster = dakMaster;

      const dakMasterCollection: IDakMaster[] = [{ id: 56918 }];
      jest.spyOn(dakMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: dakMasterCollection })));
      const additionalDakMasters = [dakMaster];
      const expectedCollection: IDakMaster[] = [...additionalDakMasters, ...dakMasterCollection];
      jest.spyOn(dakMasterService, 'addDakMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commentMaster });
      comp.ngOnInit();

      expect(dakMasterService.query).toHaveBeenCalled();
      expect(dakMasterService.addDakMasterToCollectionIfMissing).toHaveBeenCalledWith(dakMasterCollection, ...additionalDakMasters);
      expect(comp.dakMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const commentMaster: ICommentMaster = { id: 456 };
      const securityUser: ISecurityUser = { id: 10860 };
      commentMaster.securityUser = securityUser;
      const dakMaster: IDakMaster = { id: 96582 };
      commentMaster.dakMaster = dakMaster;

      activatedRoute.data = of({ commentMaster });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(commentMaster));
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.dakMastersSharedCollection).toContain(dakMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CommentMaster>>();
      const commentMaster = { id: 123 };
      jest.spyOn(commentMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commentMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commentMaster }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(commentMasterService.update).toHaveBeenCalledWith(commentMaster);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CommentMaster>>();
      const commentMaster = new CommentMaster();
      jest.spyOn(commentMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commentMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commentMaster }));
      saveSubject.complete();

      // THEN
      expect(commentMasterService.create).toHaveBeenCalledWith(commentMaster);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CommentMaster>>();
      const commentMaster = { id: 123 };
      jest.spyOn(commentMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commentMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commentMasterService.update).toHaveBeenCalledWith(commentMaster);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSecurityUserById', () => {
      it('Should return tracked SecurityUser primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDakMasterById', () => {
      it('Should return tracked DakMaster primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDakMasterById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
