import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserAccessDetailComponent } from './user-access-detail.component';

describe('UserAccess Management Detail Component', () => {
  let comp: UserAccessDetailComponent;
  let fixture: ComponentFixture<UserAccessDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserAccessDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userAccess: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserAccessDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserAccessDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userAccess on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userAccess).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
