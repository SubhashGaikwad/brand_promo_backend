import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRoleDetailComponent } from './security-role-detail.component';

describe('SecurityRole Management Detail Component', () => {
  let comp: SecurityRoleDetailComponent;
  let fixture: ComponentFixture<SecurityRoleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SecurityRoleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ securityRole: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SecurityRoleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SecurityRoleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load securityRole on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.securityRole).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
