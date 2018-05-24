import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacebookReportPostToPostComponent } from './facebook-report-post-to-post.component';

describe('FacebookReportPostToPostComponent', () => {
  let component: FacebookReportPostToPostComponent;
  let fixture: ComponentFixture<FacebookReportPostToPostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacebookReportPostToPostComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacebookReportPostToPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
