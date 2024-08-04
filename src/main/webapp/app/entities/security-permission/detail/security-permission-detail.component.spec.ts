import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityPermissionDetailComponent } from './security-permission-detail.component';

describe('SecurityPermission Management Detail Component', () => {
  let comp: SecurityPermissionDetailComponent;
  let fixture: ComponentFixture<SecurityPermissionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SecurityPermissionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ securityPermission: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SecurityPermissionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SecurityPermissionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load securityPermission on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.securityPermission).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
