import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HearingDetailsDetailComponent } from './hearing-details-detail.component';

describe('HearingDetails Management Detail Component', () => {
  let comp: HearingDetailsDetailComponent;
  let fixture: ComponentFixture<HearingDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HearingDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hearingDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HearingDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HearingDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hearingDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hearingDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
