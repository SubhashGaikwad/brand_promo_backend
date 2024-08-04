import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SecurityPermissionService } from '../service/security-permission.service';
import { ISecurityPermission, SecurityPermission } from '../security-permission.model';

import { SecurityPermissionUpdateComponent } from './security-permission-update.component';

describe('SecurityPermission Management Update Component', () => {
  let comp: SecurityPermissionUpdateComponent;
  let fixture: ComponentFixture<SecurityPermissionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let securityPermissionService: SecurityPermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SecurityPermissionUpdateComponent],
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
      .overrideTemplate(SecurityPermissionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityPermissionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    securityPermissionService = TestBed.inject(SecurityPermissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const securityPermission: ISecurityPermission = { id: 456 };

      activatedRoute.data = of({ securityPermission });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(securityPermission));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityPermission>>();
      const securityPermission = { id: 123 };
      jest.spyOn(securityPermissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityPermission }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(securityPermissionService.update).toHaveBeenCalledWith(securityPermission);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityPermission>>();
      const securityPermission = new SecurityPermission();
      jest.spyOn(securityPermissionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityPermission }));
      saveSubject.complete();

      // THEN
      expect(securityPermissionService.create).toHaveBeenCalledWith(securityPermission);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityPermission>>();
      const securityPermission = { id: 123 };
      jest.spyOn(securityPermissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(securityPermissionService.update).toHaveBeenCalledWith(securityPermission);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
