import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GhoshwaraDetailComponent } from './ghoshwara-detail.component';

describe('Ghoshwara Management Detail Component', () => {
  let comp: GhoshwaraDetailComponent;
  let fixture: ComponentFixture<GhoshwaraDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GhoshwaraDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ghoshwara: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GhoshwaraDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GhoshwaraDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ghoshwara on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ghoshwara).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
