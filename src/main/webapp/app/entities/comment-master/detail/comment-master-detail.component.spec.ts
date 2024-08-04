import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CommentMasterDetailComponent } from './comment-master-detail.component';

describe('CommentMaster Management Detail Component', () => {
  let comp: CommentMasterDetailComponent;
  let fixture: ComponentFixture<CommentMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommentMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ commentMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CommentMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CommentMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load commentMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.commentMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
