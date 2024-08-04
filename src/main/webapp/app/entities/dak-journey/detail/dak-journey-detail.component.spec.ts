import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DakJourneyDetailComponent } from './dak-journey-detail.component';

describe('DakJourney Management Detail Component', () => {
  let comp: DakJourneyDetailComponent;
  let fixture: ComponentFixture<DakJourneyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DakJourneyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dakJourney: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DakJourneyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DakJourneyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dakJourney on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dakJourney).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
