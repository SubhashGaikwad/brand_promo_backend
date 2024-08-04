import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParameterLookupDetailComponent } from './parameter-lookup-detail.component';

describe('ParameterLookup Management Detail Component', () => {
  let comp: ParameterLookupDetailComponent;
  let fixture: ComponentFixture<ParameterLookupDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParameterLookupDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ parameterLookup: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ParameterLookupDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ParameterLookupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load parameterLookup on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.parameterLookup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
