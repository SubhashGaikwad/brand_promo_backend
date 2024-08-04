import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GhoshwaraService } from '../service/ghoshwara.service';
import { IGhoshwara, Ghoshwara } from '../ghoshwara.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

import { GhoshwaraUpdateComponent } from './ghoshwara-update.component';

describe('Ghoshwara Management Update Component', () => {
  let comp: GhoshwaraUpdateComponent;
  let fixture: ComponentFixture<GhoshwaraUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ghoshwaraService: GhoshwaraService;
  let securityUserService: SecurityUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GhoshwaraUpdateComponent],
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
      .overrideTemplate(GhoshwaraUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GhoshwaraUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ghoshwaraService = TestBed.inject(GhoshwaraService);
    securityUserService = TestBed.inject(SecurityUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityUser query and add missing value', () => {
      const ghoshwara: IGhoshwara = { id: 456 };
      const securityUser: ISecurityUser = { id: 8819 };
      ghoshwara.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 91256 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ghoshwara });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ghoshwara: IGhoshwara = { id: 456 };
      const securityUser: ISecurityUser = { id: 6358 };
      ghoshwara.securityUser = securityUser;

      activatedRoute.data = of({ ghoshwara });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ghoshwara));
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Ghoshwara>>();
      const ghoshwara = { id: 123 };
      jest.spyOn(ghoshwaraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ghoshwara });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ghoshwara }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ghoshwaraService.update).toHaveBeenCalledWith(ghoshwara);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Ghoshwara>>();
      const ghoshwara = new Ghoshwara();
      jest.spyOn(ghoshwaraService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ghoshwara });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ghoshwara }));
      saveSubject.complete();

      // THEN
      expect(ghoshwaraService.create).toHaveBeenCalledWith(ghoshwara);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Ghoshwara>>();
      const ghoshwara = { id: 123 };
      jest.spyOn(ghoshwaraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ghoshwara });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ghoshwaraService.update).toHaveBeenCalledWith(ghoshwara);
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
  });
});
