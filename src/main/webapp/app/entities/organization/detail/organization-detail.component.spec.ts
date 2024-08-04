import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganizationDetailComponent } from './organization-detail.component';

describe('Organization Management Detail Component', () => {
  let comp: OrganizationDetailComponent;
  let fixture: ComponentFixture<OrganizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganizationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organization: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrganizationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organization on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organization).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
