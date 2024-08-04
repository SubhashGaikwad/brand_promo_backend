import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityUserDetailComponent } from './security-user-detail.component';

describe('SecurityUser Management Detail Component', () => {
  let comp: SecurityUserDetailComponent;
  let fixture: ComponentFixture<SecurityUserDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SecurityUserDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ securityUser: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SecurityUserDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SecurityUserDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load securityUser on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.securityUser).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
