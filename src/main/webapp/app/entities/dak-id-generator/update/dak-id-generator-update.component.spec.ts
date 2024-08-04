import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DakIdGeneratorService } from '../service/dak-id-generator.service';
import { IDakIdGenerator, DakIdGenerator } from '../dak-id-generator.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

import { DakIdGeneratorUpdateComponent } from './dak-id-generator-update.component';

describe('DakIdGenerator Management Update Component', () => {
  let comp: DakIdGeneratorUpdateComponent;
  let fixture: ComponentFixture<DakIdGeneratorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dakIdGeneratorService: DakIdGeneratorService;
  let organizationService: OrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DakIdGeneratorUpdateComponent],
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
      .overrideTemplate(DakIdGeneratorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DakIdGeneratorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dakIdGeneratorService = TestBed.inject(DakIdGeneratorService);
    organizationService = TestBed.inject(OrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call organization query and add missing value', () => {
      const dakIdGenerator: IDakIdGenerator = { id: 456 };
      const organization: IOrganization = { id: 62805 };
      dakIdGenerator.organization = organization;

      const organizationCollection: IOrganization[] = [{ id: 5817 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const expectedCollection: IOrganization[] = [organization, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dakIdGenerator });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(organizationCollection, organization);
      expect(comp.organizationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dakIdGenerator: IDakIdGenerator = { id: 456 };
      const organization: IOrganization = { id: 73385 };
      dakIdGenerator.organization = organization;

      activatedRoute.data = of({ dakIdGenerator });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dakIdGenerator));
      expect(comp.organizationsCollection).toContain(organization);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DakIdGenerator>>();
      const dakIdGenerator = { id: 123 };
      jest.spyOn(dakIdGeneratorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dakIdGenerator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dakIdGenerator }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dakIdGeneratorService.update).toHaveBeenCalledWith(dakIdGenerator);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DakIdGenerator>>();
      const dakIdGenerator = new DakIdGenerator();
      jest.spyOn(dakIdGeneratorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dakIdGenerator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dakIdGenerator }));
      saveSubject.complete();

      // THEN
      expect(dakIdGeneratorService.create).toHaveBeenCalledWith(dakIdGenerator);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DakIdGenerator>>();
      const dakIdGenerator = { id: 123 };
      jest.spyOn(dakIdGeneratorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dakIdGenerator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dakIdGeneratorService.update).toHaveBeenCalledWith(dakIdGenerator);
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
  });
});
