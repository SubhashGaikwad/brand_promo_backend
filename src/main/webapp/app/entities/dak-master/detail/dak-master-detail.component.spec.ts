import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DakMasterDetailComponent } from './dak-master-detail.component';

describe('DakMaster Management Detail Component', () => {
  let comp: DakMasterDetailComponent;
  let fixture: ComponentFixture<DakMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DakMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dakMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DakMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DakMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dakMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dakMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
