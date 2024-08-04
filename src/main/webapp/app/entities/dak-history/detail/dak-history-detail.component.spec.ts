import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DakHistoryDetailComponent } from './dak-history-detail.component';

describe('DakHistory Management Detail Component', () => {
  let comp: DakHistoryDetailComponent;
  let fixture: ComponentFixture<DakHistoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DakHistoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dakHistory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DakHistoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DakHistoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dakHistory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dakHistory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
