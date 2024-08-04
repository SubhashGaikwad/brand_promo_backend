import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DakIdGeneratorDetailComponent } from './dak-id-generator-detail.component';

describe('DakIdGenerator Management Detail Component', () => {
  let comp: DakIdGeneratorDetailComponent;
  let fixture: ComponentFixture<DakIdGeneratorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DakIdGeneratorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dakIdGenerator: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DakIdGeneratorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DakIdGeneratorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dakIdGenerator on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dakIdGenerator).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
