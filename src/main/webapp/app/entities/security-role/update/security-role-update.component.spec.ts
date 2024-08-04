import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SecurityRoleService } from '../service/security-role.service';
import { ISecurityRole, SecurityRole } from '../security-role.model';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';

import { SecurityRoleUpdateComponent } from './security-role-update.component';

describe('SecurityRole Management Update Component', () => {
  let comp: SecurityRoleUpdateComponent;
  let fixture: ComponentFixture<SecurityRoleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let securityRoleService: SecurityRoleService;
  let securityPermissionService: SecurityPermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SecurityRoleUpdateComponent],
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
      .overrideTemplate(SecurityRoleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityRoleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    securityRoleService = TestBed.inject(SecurityRoleService);
    securityPermissionService = TestBed.inject(SecurityPermissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityPermission query and add missing value', () => {
      const securityRole: ISecurityRole = { id: 456 };
      const securityPermissions: ISecurityPermission[] = [{ id: 40983 }];
      securityRole.securityPermissions = securityPermissions;

      const securityPermissionCollection: ISecurityPermission[] = [{ id: 9606 }];
      jest.spyOn(securityPermissionService, 'query').mockReturnValue(of(new HttpResponse({ body: securityPermissionCollection })));
      const additionalSecurityPermissions = [...securityPermissions];
      const expectedCollection: ISecurityPermission[] = [...additionalSecurityPermissions, ...securityPermissionCollection];
      jest.spyOn(securityPermissionService, 'addSecurityPermissionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityRole });
      comp.ngOnInit();

      expect(securityPermissionService.query).toHaveBeenCalled();
      expect(securityPermissionService.addSecurityPermissionToCollectionIfMissing).toHaveBeenCalledWith(
        securityPermissionCollection,
        ...additionalSecurityPermissions
      );
      expect(comp.securityPermissionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const securityRole: ISecurityRole = { id: 456 };
      const securityPermissions: ISecurityPermission = { id: 56800 };
      securityRole.securityPermissions = [securityPermissions];

      activatedRoute.data = of({ securityRole });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(securityRole));
      expect(comp.securityPermissionsSharedCollection).toContain(securityPermissions);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityRole>>();
      const securityRole = { id: 123 };
      jest.spyOn(securityRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityRole }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(securityRoleService.update).toHaveBeenCalledWith(securityRole);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityRole>>();
      const securityRole = new SecurityRole();
      jest.spyOn(securityRoleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityRole }));
      saveSubject.complete();

      // THEN
      expect(securityRoleService.create).toHaveBeenCalledWith(securityRole);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityRole>>();
      const securityRole = { id: 123 };
      jest.spyOn(securityRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(securityRoleService.update).toHaveBeenCalledWith(securityRole);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSecurityPermissionById', () => {
      it('Should return tracked SecurityPermission primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityPermissionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedSecurityPermission', () => {
      it('Should return option if no SecurityPermission is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedSecurityPermission(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected SecurityPermission for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedSecurityPermission(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this SecurityPermission is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedSecurityPermission(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
