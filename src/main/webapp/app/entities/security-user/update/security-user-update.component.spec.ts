import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SecurityUserService } from '../service/security-user.service';
import { ISecurityUser, SecurityUser } from '../security-user.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { SecurityRoleService } from 'app/entities/security-role/service/security-role.service';

import { SecurityUserUpdateComponent } from './security-user-update.component';

describe('SecurityUser Management Update Component', () => {
  let comp: SecurityUserUpdateComponent;
  let fixture: ComponentFixture<SecurityUserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let securityUserService: SecurityUserService;
  let organizationService: OrganizationService;
  let securityPermissionService: SecurityPermissionService;
  let securityRoleService: SecurityRoleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SecurityUserUpdateComponent],
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
      .overrideTemplate(SecurityUserUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityUserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    securityUserService = TestBed.inject(SecurityUserService);
    organizationService = TestBed.inject(OrganizationService);
    securityPermissionService = TestBed.inject(SecurityPermissionService);
    securityRoleService = TestBed.inject(SecurityRoleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Organization query and add missing value', () => {
      const securityUser: ISecurityUser = { id: 456 };
      const organization: IOrganization = { id: 76136 };
      securityUser.organization = organization;

      const organizationCollection: IOrganization[] = [{ id: 36501 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organization];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityPermission query and add missing value', () => {
      const securityUser: ISecurityUser = { id: 456 };
      const securityPermissions: ISecurityPermission[] = [{ id: 39938 }];
      securityUser.securityPermissions = securityPermissions;

      const securityPermissionCollection: ISecurityPermission[] = [{ id: 31986 }];
      jest.spyOn(securityPermissionService, 'query').mockReturnValue(of(new HttpResponse({ body: securityPermissionCollection })));
      const additionalSecurityPermissions = [...securityPermissions];
      const expectedCollection: ISecurityPermission[] = [...additionalSecurityPermissions, ...securityPermissionCollection];
      jest.spyOn(securityPermissionService, 'addSecurityPermissionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      expect(securityPermissionService.query).toHaveBeenCalled();
      expect(securityPermissionService.addSecurityPermissionToCollectionIfMissing).toHaveBeenCalledWith(
        securityPermissionCollection,
        ...additionalSecurityPermissions
      );
      expect(comp.securityPermissionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityRole query and add missing value', () => {
      const securityUser: ISecurityUser = { id: 456 };
      const securityRoles: ISecurityRole[] = [{ id: 78752 }];
      securityUser.securityRoles = securityRoles;

      const securityRoleCollection: ISecurityRole[] = [{ id: 72788 }];
      jest.spyOn(securityRoleService, 'query').mockReturnValue(of(new HttpResponse({ body: securityRoleCollection })));
      const additionalSecurityRoles = [...securityRoles];
      const expectedCollection: ISecurityRole[] = [...additionalSecurityRoles, ...securityRoleCollection];
      jest.spyOn(securityRoleService, 'addSecurityRoleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      expect(securityRoleService.query).toHaveBeenCalled();
      expect(securityRoleService.addSecurityRoleToCollectionIfMissing).toHaveBeenCalledWith(
        securityRoleCollection,
        ...additionalSecurityRoles
      );
      expect(comp.securityRolesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const securityUser: ISecurityUser = { id: 456 };
      const organization: IOrganization = { id: 42467 };
      securityUser.organization = organization;
      const securityPermissions: ISecurityPermission = { id: 79826 };
      securityUser.securityPermissions = [securityPermissions];
      const securityRoles: ISecurityRole = { id: 91779 };
      securityUser.securityRoles = [securityRoles];

      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(securityUser));
      expect(comp.organizationsSharedCollection).toContain(organization);
      expect(comp.securityPermissionsSharedCollection).toContain(securityPermissions);
      expect(comp.securityRolesSharedCollection).toContain(securityRoles);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityUser>>();
      const securityUser = { id: 123 };
      jest.spyOn(securityUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityUser }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(securityUserService.update).toHaveBeenCalledWith(securityUser);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityUser>>();
      const securityUser = new SecurityUser();
      jest.spyOn(securityUserService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityUser }));
      saveSubject.complete();

      // THEN
      expect(securityUserService.create).toHaveBeenCalledWith(securityUser);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityUser>>();
      const securityUser = { id: 123 };
      jest.spyOn(securityUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(securityUserService.update).toHaveBeenCalledWith(securityUser);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackOrganizationById', () => {
      it('Should return tracked Organization primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrganizationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSecurityPermissionById', () => {
      it('Should return tracked SecurityPermission primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityPermissionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSecurityRoleById', () => {
      it('Should return tracked SecurityRole primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityRoleById(0, entity);
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

    describe('getSelectedSecurityRole', () => {
      it('Should return option if no SecurityRole is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedSecurityRole(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected SecurityRole for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedSecurityRole(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this SecurityRole is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedSecurityRole(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
